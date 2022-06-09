/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
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

package io.github.kaygo.internal.webclient.keycloak.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicAuthHelper
{
    public static String createHeader(String username, String password) {
        StringBuilder sb = new StringBuilder();
        sb.append(username).append(':').append(password);
        return "Basic " + Base64.getEncoder().encodeToString(sb.toString().getBytes(StandardCharsets.UTF_8));
    }

    public static String[] parseHeader(String header) {
        if (header.length() < 6) return null;
        String type = header.substring(0, 5);
        type = type.toLowerCase();
        if (!type.equalsIgnoreCase("Basic")) return null;
        String val = header.substring(6);
        val = new String(Base64.getDecoder().decode(val.getBytes()));
        int seperatorIndex = val.indexOf(":");
        if(seperatorIndex == -1) return null;
        String user = val.substring(0, seperatorIndex);
        String pw = val.substring(seperatorIndex + 1);
        return new String[]{user,pw};
    }
}
