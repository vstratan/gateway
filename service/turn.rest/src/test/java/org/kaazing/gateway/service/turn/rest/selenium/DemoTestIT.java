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

import org.junit.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;

import java.net.MalformedURLException;

import java.io.IOException;

public class DemoTestIT {

    private DemoPage page;
    private DemoNavigationSteps demoNavigationSteps;
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

        page = PageFactory.initElements(new ChromeDriver(capabilities), DemoPage.class);
        page.open(GW_URL);

        demoNavigationSteps = new DemoNavigationSteps(page.getDriver());
    }


    @After
    public void closeTheBrowser() {
        page.close();
    }

    @Test
    public void testWebRTC() throws InterruptedException, IOException {
        page.inputUsername("test1");
        page.clickOnSignIn();
        demoNavigationSteps.openNewTab(GW_URL, page);
        page.inputUsername("test2");
        page.clickOnSignIn();
        page.inputUsernameToCall("test1");
        page.clickOnCallBtn();

        Assert.assertTrue(page.isLocalVideoStreaming());
        Assert.assertTrue(page.isRemoteVideoStreaming());
        demoNavigationSteps.switchTabPrevious();
        Thread.sleep(1000);
        Assert.assertTrue(page.isLocalVideoStreaming());
        Assert.assertTrue(page.isRemoteVideoStreaming());

    }

}
