/*
 * Copyright (C) 2014 Dallas Gutauckis (http://dallasgutauckis.com)
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

import pl.charmas.parcelablegenerator.typeserializers.SerializableValue;
import pl.charmas.parcelablegenerator.typeserializers.TypeSerializer;
import pl.charmas.parcelablegenerator.util.PsiUtils;

public class SparseArraySerializer implements TypeSerializer {
    @Override
    public String writeValue(SerializableValue field, String parcel, String flags) {
        return String.format("%s.writeSparseArray((android.util.SparseArray) %s);", parcel, field.getName());
    }

    @Override
    public String readValue(SerializableValue field, String parcel) {
        String paramType = PsiUtils.getResolvedGenerics(field.getType()).get(0).getCanonicalText();
        return String.format("%s = %s.readSparseArray(%s.class.getClassLoader());", field.getName(), parcel, paramType);
    }
}
