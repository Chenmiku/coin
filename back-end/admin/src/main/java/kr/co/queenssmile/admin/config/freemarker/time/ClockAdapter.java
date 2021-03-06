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

import java.time.Clock;

/**
 * ClockAdapter adds basic format support for {@link Clock} too FreeMarker 2.3.23 and above.
 */
public class ClockAdapter extends AbstractAdapter<Clock> implements AdapterTemplateModel,
    TemplateScalarModel, TemplateHashModel {

    public ClockAdapter(Clock obj) {
        super(obj);
    }

    @Override
    public TemplateModel get(String s) throws TemplateModelException {
        if (DateTimeTools.METHOD_FORMAT.equals(s)) {
            return new ClockFormatter(getObject());
        }
        throw new TemplateModelException(DateTimeTools.METHOD_UNKNOWN_MSG + s);
    }

    /**
     * This is a quit silly implementation. Normally you would like to convert to an Instant when printing the clock.
     *
     * @return String representation of the clock.
     * @throws TemplateModelException If no string representation could be created.
     */
    @Override
    public String getAsString() throws TemplateModelException {
        return getObject().toString();
    }

    public class ClockFormatter extends AbstractFormatter<Clock> implements TemplateMethodModelEx {

        public ClockFormatter(Clock obj) {
            super(obj);
        }

        @Override
        public Object exec(java.util.List list) throws TemplateModelException {
            return getObject().toString();
        }
    }
}
