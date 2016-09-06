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
package org.kaazing.gateway.service.turn.rest.selenium;;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DemoPage {

    private WebDriver driver;
    private JavascriptExecutor js;

    @FindBy(id = "usernameInput")
    WebElement usernameInput;

    @FindBy(id = "passwordInput")
    WebElement passwordInput;

    @FindBy(id = "loginBtn")
    WebElement loginBtn;

    @FindBy(id = "callToUsernameInput")
    WebElement callToUsernameInput;

    @FindBy(id = "callBtn")
    WebElement callBtn;

    @FindBy(id = "callToUsernameInput")
    WebElement hangUpBtn;

    @FindBy(id = "localVideo")
    WebElement localVideo;

    @FindBy(id = "remoteVideo")
    WebElement remoteVideo;


    public DemoPage(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        js = (JavascriptExecutor) driver;
    }

    public void open(String url) {
        driver.get(url);
    }

    public void close() {
        driver.quit();
    }

    public void inputUsername(String name) {
        usernameInput.clear();
        usernameInput.sendKeys(name);
    }

    public void inputPassword(String pass) {
        usernameInput.clear();
        usernameInput.sendKeys(pass);
    }

    public void clickOnSignIn() {
        loginBtn.click();
    }

    public void inputUsernameToCall(String name) {
        callToUsernameInput.clear();
        callToUsernameInput.sendKeys(name);
    }

    public void clickOnCallBtn() {
        callBtn.click();
        // wait for connection to establish after clicking on call
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickOnHangBtn() {
        hangUpBtn.click();
    }

    public WebDriver getDriver(){
        return driver;
    }

    public boolean isRemoteVideoStreaming(){
        return isVideoStreaming(remoteVideo);
    }

    public boolean isLocalVideoStreaming(){
        return isVideoStreaming(localVideo);
    }

    private boolean isVideoStreaming(WebElement videoElement){
        return ((Number)js.executeScript("return arguments[0].currentTime;", videoElement)).doubleValue() > 0  &&
                ((ArrayList<Object>)js.executeScript("return arguments[0].played;", videoElement)).size() > 0;
    }

}

