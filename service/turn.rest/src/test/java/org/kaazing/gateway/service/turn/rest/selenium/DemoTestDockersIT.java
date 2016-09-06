/**
 * Copyright 2007-2016, Kaazing Corporation. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kaazing.gateway.service.turn.rest.selenium;

import com.palantir.docker.compose.DockerComposeRule;
import org.junit.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DemoTestDockersIT {

    @ClassRule
    public static DockerComposeRule docker = DockerComposeRule.builder()
            .file("src/test/resources/webRtcDemoSetup/docker-compose.yml")
            .build();

    private DemoPage node1DemoPage;
    private DemoPage node2DemoPage;
    private final String node1Username = "test1";
    private final String node2Username = "test2";
    private final String GW_URL = "https://gateway.kaazing.webrtc.demo:18000/";

    @Before
    public void openTheBrowser() throws MalformedURLException {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--allow-file-access-from-files",
                "--use-fake-ui-for-media-stream",
                "--allow-file-access",
                "--use-file-for-fake-audio-capture=D:\\PATH\\TO\\WAV\\xxx.wav",
                "--use-fake-device-for-media-stream");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        node1DemoPage = PageFactory.initElements(new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"), capabilities), DemoPage.class);
        node2DemoPage = PageFactory.initElements(new RemoteWebDriver(new URL("http://192.168.99.100:4445/wd/hub"), capabilities), DemoPage.class);
    }


    @After
    public void closeTheBrowser() {
        node1DemoPage.close();
        node2DemoPage.close();
    }

    @Test
    public void testWebRTC() throws InterruptedException, IOException {

        node1DemoPage.open(GW_URL);
        node1DemoPage.inputUsername(node1Username);
        node1DemoPage.clickOnSignIn();

        node2DemoPage.open(GW_URL);
        node2DemoPage.inputUsername(node2Username);
        node2DemoPage.clickOnSignIn();
        node2DemoPage.inputUsernameToCall(node1Username);
        node2DemoPage.clickOnCallBtn();

        Assert.assertTrue(node1DemoPage.isLocalVideoStreaming());
        Assert.assertTrue(node1DemoPage.isRemoteVideoStreaming());
        Assert.assertTrue(node2DemoPage.isLocalVideoStreaming());
        Assert.assertTrue(node2DemoPage.isRemoteVideoStreaming());
    }

}
