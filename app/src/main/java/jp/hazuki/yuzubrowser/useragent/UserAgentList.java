/*
 * Copyright (C) 2017 Hazuki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.hazuki.yuzubrowser.useragent;

import android.content.Context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import jp.hazuki.yuzubrowser.utils.ErrorReport;

public class UserAgentList extends ArrayList<UserAgent> {
    private static final String FILENAME = "ualist_1.dat";

    public boolean read(Context context) {
        clear();

        File file = context.getFileStreamPath(FILENAME);

        if (file == null || !file.exists() || file.isDirectory()) return true;
        try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {

            List<UserAgent> userAgents = new ObjectMapper().readValue(is, new TypeReference<List<UserAgent>>() {
            });
            addAll(userAgents);

            return true;
        } catch (IOException e) {
            ErrorReport.printAndWriteLog(e);
        }
        return false;
    }

    public boolean write(Context context) {
        File file = context.getFileStreamPath(FILENAME);

        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(file))) {

            new ObjectMapper().writeValue(os, this);
            return true;

        } catch (IOException e) {
            ErrorReport.printAndWriteLog(e);
        }
        return false;
    }
}
