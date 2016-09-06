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
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

public class DemoNavigationSteps {

    WebDriver driver;
    private int currentTabIndex = 0;
    private int parentTabIndex = 0;
    ArrayList<String> tabs;

    public DemoNavigationSteps(WebDriver driver){
        this.driver = driver;
    }

    public void openNewTab(String address, DemoPage page){

        // TODO switch to iterator
        driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
        tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(currentTabIndex + 1));
        driver.navigate().to(address);
        parentTabIndex = currentTabIndex;
        currentTabIndex++;
    }

    public void switchTabNext(){
        driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "\t");
        driver.switchTo().window(tabs.get(currentTabIndex + 1));
        parentTabIndex = currentTabIndex;
    }

    public void switchTabPrevious(){
        String selectAll = Keys.chord(Keys.CONTROL, Keys.SHIFT,"\t");
        driver.findElement(By.cssSelector("body")).sendKeys(selectAll);
        driver.switchTo().window(tabs.get(currentTabIndex - 1));
        parentTabIndex = currentTabIndex-2;

    }

    public void closeCurrentTab(){
        driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "w");
        driver.switchTo().window(tabs.get(parentTabIndex));
        currentTabIndex--;
        parentTabIndex--;
        tabs = new ArrayList<>(driver.getWindowHandles());
    }

}
