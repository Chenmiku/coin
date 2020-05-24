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

import java.time.Period;

/**
 * PeriodAdapter gives access to the days, months and years values in the wrapped {@link Period} object in FreeMarker
 * templates.
 */
public class PeriodAdapter extends AbstractAdapter<Period> implements AdapterTemplateModel,
    TemplateScalarModel, TemplateHashModel {


    public PeriodAdapter(Period obj) {
        super(obj);
    }

    @Override
    public TemplateModel get(String s) throws TemplateModelException {
        if (DateTimeTools.METHOD_DAYS.equalsIgnoreCase(s)) {
            return new SimpleNumber(getObject().getDays());
        } else if (DateTimeTools.METHOD_MONTHS.equalsIgnoreCase(s)) {
            return new SimpleNumber(getObject().getMonths());
        } else if (DateTimeTools.METHOD_YEARS.equalsIgnoreCase(s)) {
            return new SimpleNumber(getObject().getYears());
        }
        throw new TemplateModelException(DateTimeTools.METHOD_UNKNOWN_MSG + s);
    }

}
