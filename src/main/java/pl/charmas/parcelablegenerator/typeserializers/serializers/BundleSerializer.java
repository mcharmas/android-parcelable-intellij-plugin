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

import pl.charmas.parcelablegenerator.typeserializers.SerializableValue;
import pl.charmas.parcelablegenerator.typeserializers.TypeSerializer;

/**
 * Custom serializer for Date objects to simplify parceling
 *
 * @author Dallas Gutauckis [dallas@gutauckis.com]
 */
public class BundleSerializer implements TypeSerializer {

    @Override
    public String writeValue(SerializableValue field, String parcel, String flags) {
        return parcel + ".writeBundle(" + field.getName() + ");";
    }

    @Override
    public String readValue(SerializableValue field, String parcel) {
        return field.getName() + " = " + parcel + ".readBundle();";
    }
}
