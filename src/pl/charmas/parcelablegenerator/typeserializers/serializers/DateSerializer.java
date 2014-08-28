/*
 * Copyright (C) 2013 Michał Charmas (http://blog.charmas.pl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.charmas.parcelablegenerator.typeserializers.serializers;

import com.intellij.psi.PsiField;
import org.apache.xmlbeans.impl.common.NameUtil;
import pl.charmas.parcelablegenerator.typeserializers.TypeSerializer;

/**
 * Custom serializer for Date objects to simplify parceling
 *
 * @author Dallas Gutauckis [dallas@gutauckis.com]
 */
public class DateSerializer implements TypeSerializer {

    private static final String NULL_VALUE = "-1";

    @Override
    public String writeValue(PsiField field, String parcel, String flags) {
        String fieldName = field.getName();
        return String.format("%s.writeLong(%s != null ? %s.getTime() : %s);", parcel, fieldName, fieldName, NULL_VALUE);
    }

    @Override
    public String readValue(PsiField field, String parcel) {
        String fieldName = field.getName();
        String tmpFieldName = NameUtil.upperCaseFirstLetter(fieldName);
        String formatted = String.format("long tmp%s = %s.readLong(); " +
                "this.%s = tmp%s == %s ? null : new java.util.Date(tmp%s);", tmpFieldName, parcel, fieldName, tmpFieldName, NULL_VALUE, tmpFieldName);
        return formatted;
    }
}
