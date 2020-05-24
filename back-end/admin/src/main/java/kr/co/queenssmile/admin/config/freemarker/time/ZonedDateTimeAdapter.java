/*
 * Copyright (c) 2015-2015 Amedia Utvikling AS.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kr.co.queenssmile.admin.config.freemarker.time;

import freemarker.template.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ZonedDateTimeAdapter adds basic format support for {@link ZonedDateTime} too FreeMarker 2.3.23 and above.
 */
public class ZonedDateTimeAdapter extends AbstractAdapter<ZonedDateTime> implements AdapterTemplateModel,
    TemplateScalarModel, TemplateHashModel {

    public ZonedDateTimeAdapter(ZonedDateTime obj) {
        super(obj);
    }

    public TemplateModel get(String s) throws TemplateModelException {
        if (DateTimeTools.METHOD_FORMAT.equals(s)) {
            return new ZonedDateTimeFormatter(getObject());
        }
        throw new TemplateModelException(DateTimeTools.METHOD_UNKNOWN_MSG + s);
    }

    public class ZonedDateTimeFormatter extends AbstractFormatter<ZonedDateTime> implements TemplateMethodModelEx {

        public ZonedDateTimeFormatter(ZonedDateTime obj) {
            super(obj);
        }

        public Object exec(java.util.List list) throws TemplateModelException {
            ZoneId zoneId = DateTimeTools.zoneIdLookup(list, 1);
            if (isDifferentTimeZoneRequested(zoneId)) {
                return getObject().withZoneSameInstant(zoneId).format(
                        DateTimeTools.createDateTimeFormatter(list, 0, DateTimeFormatter.ISO_ZONED_DATE_TIME));
            } else {
                return getObject().format(DateTimeTools.createDateTimeFormatter(list, 0, DateTimeFormatter.ISO_ZONED_DATE_TIME));
            }
        }

        private boolean isDifferentTimeZoneRequested(ZoneId zoneId) {
            return !getObject().getZone().equals(zoneId);
        }


    }

}
