package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsList;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.Property;
import io.realm.internal.ProxyUtils;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("all")
public class ImagesRealmProxy extends com.directoriodelicias.apps.delicias.classes.Images
    implements RealmObjectProxy, ImagesRealmProxyInterface {

    static final class ImagesColumnInfo extends ColumnInfo {
        long url200_200Index;
        long url500_500Index;
        long url100_100Index;
        long heightIndex;
        long widthIndex;
        long mJsonIndex;
        long idIndex;
        long hashtagsIndex;
        long typeIndex;
        long urlFullIndex;

        ImagesColumnInfo(OsSchemaInfo schemaInfo) {
            super(10);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("Images");
            this.url200_200Index = addColumnDetails("url200_200", objectSchemaInfo);
            this.url500_500Index = addColumnDetails("url500_500", objectSchemaInfo);
            this.url100_100Index = addColumnDetails("url100_100", objectSchemaInfo);
            this.heightIndex = addColumnDetails("height", objectSchemaInfo);
            this.widthIndex = addColumnDetails("width", objectSchemaInfo);
            this.mJsonIndex = addColumnDetails("mJson", objectSchemaInfo);
            this.idIndex = addColumnDetails("id", objectSchemaInfo);
            this.hashtagsIndex = addColumnDetails("hashtags", objectSchemaInfo);
            this.typeIndex = addColumnDetails("type", objectSchemaInfo);
            this.urlFullIndex = addColumnDetails("urlFull", objectSchemaInfo);
        }

        ImagesColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new ImagesColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final ImagesColumnInfo src = (ImagesColumnInfo) rawSrc;
            final ImagesColumnInfo dst = (ImagesColumnInfo) rawDst;
            dst.url200_200Index = src.url200_200Index;
            dst.url500_500Index = src.url500_500Index;
            dst.url100_100Index = src.url100_100Index;
            dst.heightIndex = src.heightIndex;
            dst.widthIndex = src.widthIndex;
            dst.mJsonIndex = src.mJsonIndex;
            dst.idIndex = src.idIndex;
            dst.hashtagsIndex = src.hashtagsIndex;
            dst.typeIndex = src.typeIndex;
            dst.urlFullIndex = src.urlFullIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>(10);
        fieldNames.add("url200_200");
        fieldNames.add("url500_500");
        fieldNames.add("url100_100");
        fieldNames.add("height");
        fieldNames.add("width");
        fieldNames.add("mJson");
        fieldNames.add("id");
        fieldNames.add("hashtags");
        fieldNames.add("type");
        fieldNames.add("urlFull");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private ImagesColumnInfo columnInfo;
    private ProxyState<com.directoriodelicias.apps.delicias.classes.Images> proxyState;

    ImagesRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (ImagesColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.directoriodelicias.apps.delicias.classes.Images>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$url200_200() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.url200_200Index);
    }

    @Override
    public void realmSet$url200_200(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.url200_200Index, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.url200_200Index, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.url200_200Index);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.url200_200Index, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$url500_500() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.url500_500Index);
    }

    @Override
    public void realmSet$url500_500(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.url500_500Index, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.url500_500Index, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.url500_500Index);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.url500_500Index, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$url100_100() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.url100_100Index);
    }

    @Override
    public void realmSet$url100_100(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.url100_100Index, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.url100_100Index, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.url100_100Index);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.url100_100Index, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$height() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.heightIndex);
    }

    @Override
    public void realmSet$height(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.heightIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.heightIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$width() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.widthIndex);
    }

    @Override
    public void realmSet$width(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.widthIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.widthIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$mJson() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.mJsonIndex);
    }

    @Override
    public void realmSet$mJson(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.mJsonIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.mJsonIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.mJsonIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.mJsonIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$id() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.idIndex);
    }

    @Override
    public void realmSet$id(String value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'id' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$hashtags() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.hashtagsIndex);
    }

    @Override
    public void realmSet$hashtags(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.hashtagsIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.hashtagsIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.hashtagsIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.hashtagsIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$type() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.typeIndex);
    }

    @Override
    public void realmSet$type(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.typeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.typeIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$urlFull() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.urlFullIndex);
    }

    @Override
    public void realmSet$urlFull(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.urlFullIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.urlFullIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.urlFullIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.urlFullIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("Images", 10, 0);
        builder.addPersistedProperty("url200_200", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("url500_500", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("url100_100", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("height", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("width", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("mJson", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("id", RealmFieldType.STRING, Property.PRIMARY_KEY, Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("hashtags", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("type", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("urlFull", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static ImagesColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new ImagesColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "Images";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.directoriodelicias.apps.delicias.classes.Images createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.directoriodelicias.apps.delicias.classes.Images obj = null;
        if (update) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Images.class);
            ImagesColumnInfo columnInfo = (ImagesColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Images.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = Table.NO_MATCH;
            if (json.isNull("id")) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstString(pkColumnIndex, json.getString("id"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Images.class), false, Collections.<String> emptyList());
                    obj = new io.realm.ImagesRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.ImagesRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Images.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.ImagesRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Images.class, json.getString("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }

        final ImagesRealmProxyInterface objProxy = (ImagesRealmProxyInterface) obj;
        if (json.has("url200_200")) {
            if (json.isNull("url200_200")) {
                objProxy.realmSet$url200_200(null);
            } else {
                objProxy.realmSet$url200_200((String) json.getString("url200_200"));
            }
        }
        if (json.has("url500_500")) {
            if (json.isNull("url500_500")) {
                objProxy.realmSet$url500_500(null);
            } else {
                objProxy.realmSet$url500_500((String) json.getString("url500_500"));
            }
        }
        if (json.has("url100_100")) {
            if (json.isNull("url100_100")) {
                objProxy.realmSet$url100_100(null);
            } else {
                objProxy.realmSet$url100_100((String) json.getString("url100_100"));
            }
        }
        if (json.has("height")) {
            if (json.isNull("height")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'height' to null.");
            } else {
                objProxy.realmSet$height((int) json.getInt("height"));
            }
        }
        if (json.has("width")) {
            if (json.isNull("width")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'width' to null.");
            } else {
                objProxy.realmSet$width((int) json.getInt("width"));
            }
        }
        if (json.has("mJson")) {
            if (json.isNull("mJson")) {
                objProxy.realmSet$mJson(null);
            } else {
                objProxy.realmSet$mJson((String) json.getString("mJson"));
            }
        }
        if (json.has("hashtags")) {
            if (json.isNull("hashtags")) {
                objProxy.realmSet$hashtags(null);
            } else {
                objProxy.realmSet$hashtags((String) json.getString("hashtags"));
            }
        }
        if (json.has("type")) {
            if (json.isNull("type")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'type' to null.");
            } else {
                objProxy.realmSet$type((int) json.getInt("type"));
            }
        }
        if (json.has("urlFull")) {
            if (json.isNull("urlFull")) {
                objProxy.realmSet$urlFull(null);
            } else {
                objProxy.realmSet$urlFull((String) json.getString("urlFull"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.directoriodelicias.apps.delicias.classes.Images createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.directoriodelicias.apps.delicias.classes.Images obj = new com.directoriodelicias.apps.delicias.classes.Images();
        final ImagesRealmProxyInterface objProxy = (ImagesRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("url200_200")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$url200_200((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$url200_200(null);
                }
            } else if (name.equals("url500_500")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$url500_500((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$url500_500(null);
                }
            } else if (name.equals("url100_100")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$url100_100((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$url100_100(null);
                }
            } else if (name.equals("height")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$height((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'height' to null.");
                }
            } else if (name.equals("width")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$width((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'width' to null.");
                }
            } else if (name.equals("mJson")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$mJson((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$mJson(null);
                }
            } else if (name.equals("id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$id((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$id(null);
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("hashtags")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$hashtags((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$hashtags(null);
                }
            } else if (name.equals("type")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$type((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'type' to null.");
                }
            } else if (name.equals("urlFull")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$urlFull((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$urlFull(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
        }
        return realm.copyToRealm(obj);
    }

    public static com.directoriodelicias.apps.delicias.classes.Images copyOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.Images object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null) {
            final BaseRealm otherRealm = ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm();
            if (otherRealm.threadId != realm.threadId) {
                throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
            }
            if (otherRealm.getPath().equals(realm.getPath())) {
                return object;
            }
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.directoriodelicias.apps.delicias.classes.Images) cachedRealmObject;
        }

        com.directoriodelicias.apps.delicias.classes.Images realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Images.class);
            ImagesColumnInfo columnInfo = (ImagesColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Images.class);
            long pkColumnIndex = columnInfo.idIndex;
            String value = ((ImagesRealmProxyInterface) object).realmGet$id();
            long rowIndex = Table.NO_MATCH;
            if (value == null) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstString(pkColumnIndex, value);
            }
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Images.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.ImagesRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.directoriodelicias.apps.delicias.classes.Images copy(Realm realm, com.directoriodelicias.apps.delicias.classes.Images newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.directoriodelicias.apps.delicias.classes.Images) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.directoriodelicias.apps.delicias.classes.Images realmObject = realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Images.class, ((ImagesRealmProxyInterface) newObject).realmGet$id(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        ImagesRealmProxyInterface realmObjectSource = (ImagesRealmProxyInterface) newObject;
        ImagesRealmProxyInterface realmObjectCopy = (ImagesRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$url200_200(realmObjectSource.realmGet$url200_200());
        realmObjectCopy.realmSet$url500_500(realmObjectSource.realmGet$url500_500());
        realmObjectCopy.realmSet$url100_100(realmObjectSource.realmGet$url100_100());
        realmObjectCopy.realmSet$height(realmObjectSource.realmGet$height());
        realmObjectCopy.realmSet$width(realmObjectSource.realmGet$width());
        realmObjectCopy.realmSet$mJson(realmObjectSource.realmGet$mJson());
        realmObjectCopy.realmSet$hashtags(realmObjectSource.realmGet$hashtags());
        realmObjectCopy.realmSet$type(realmObjectSource.realmGet$type());
        realmObjectCopy.realmSet$urlFull(realmObjectSource.realmGet$urlFull());
        return realmObject;
    }

    public static long insert(Realm realm, com.directoriodelicias.apps.delicias.classes.Images object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Images.class);
        long tableNativePtr = table.getNativePtr();
        ImagesColumnInfo columnInfo = (ImagesColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Images.class);
        long pkColumnIndex = columnInfo.idIndex;
        String primaryKeyValue = ((ImagesRealmProxyInterface) object).realmGet$id();
        long rowIndex = Table.NO_MATCH;
        if (primaryKeyValue == null) {
            rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
        } else {
            rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, primaryKeyValue);
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$url200_200 = ((ImagesRealmProxyInterface) object).realmGet$url200_200();
        if (realmGet$url200_200 != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.url200_200Index, rowIndex, realmGet$url200_200, false);
        }
        String realmGet$url500_500 = ((ImagesRealmProxyInterface) object).realmGet$url500_500();
        if (realmGet$url500_500 != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.url500_500Index, rowIndex, realmGet$url500_500, false);
        }
        String realmGet$url100_100 = ((ImagesRealmProxyInterface) object).realmGet$url100_100();
        if (realmGet$url100_100 != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.url100_100Index, rowIndex, realmGet$url100_100, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.heightIndex, rowIndex, ((ImagesRealmProxyInterface) object).realmGet$height(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.widthIndex, rowIndex, ((ImagesRealmProxyInterface) object).realmGet$width(), false);
        String realmGet$mJson = ((ImagesRealmProxyInterface) object).realmGet$mJson();
        if (realmGet$mJson != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.mJsonIndex, rowIndex, realmGet$mJson, false);
        }
        String realmGet$hashtags = ((ImagesRealmProxyInterface) object).realmGet$hashtags();
        if (realmGet$hashtags != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.hashtagsIndex, rowIndex, realmGet$hashtags, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.typeIndex, rowIndex, ((ImagesRealmProxyInterface) object).realmGet$type(), false);
        String realmGet$urlFull = ((ImagesRealmProxyInterface) object).realmGet$urlFull();
        if (realmGet$urlFull != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.urlFullIndex, rowIndex, realmGet$urlFull, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Images.class);
        long tableNativePtr = table.getNativePtr();
        ImagesColumnInfo columnInfo = (ImagesColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Images.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.directoriodelicias.apps.delicias.classes.Images object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.Images) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            String primaryKeyValue = ((ImagesRealmProxyInterface) object).realmGet$id();
            long rowIndex = Table.NO_MATCH;
            if (primaryKeyValue == null) {
                rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
            } else {
                rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, primaryKeyValue);
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$url200_200 = ((ImagesRealmProxyInterface) object).realmGet$url200_200();
            if (realmGet$url200_200 != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.url200_200Index, rowIndex, realmGet$url200_200, false);
            }
            String realmGet$url500_500 = ((ImagesRealmProxyInterface) object).realmGet$url500_500();
            if (realmGet$url500_500 != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.url500_500Index, rowIndex, realmGet$url500_500, false);
            }
            String realmGet$url100_100 = ((ImagesRealmProxyInterface) object).realmGet$url100_100();
            if (realmGet$url100_100 != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.url100_100Index, rowIndex, realmGet$url100_100, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.heightIndex, rowIndex, ((ImagesRealmProxyInterface) object).realmGet$height(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.widthIndex, rowIndex, ((ImagesRealmProxyInterface) object).realmGet$width(), false);
            String realmGet$mJson = ((ImagesRealmProxyInterface) object).realmGet$mJson();
            if (realmGet$mJson != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.mJsonIndex, rowIndex, realmGet$mJson, false);
            }
            String realmGet$hashtags = ((ImagesRealmProxyInterface) object).realmGet$hashtags();
            if (realmGet$hashtags != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.hashtagsIndex, rowIndex, realmGet$hashtags, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.typeIndex, rowIndex, ((ImagesRealmProxyInterface) object).realmGet$type(), false);
            String realmGet$urlFull = ((ImagesRealmProxyInterface) object).realmGet$urlFull();
            if (realmGet$urlFull != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.urlFullIndex, rowIndex, realmGet$urlFull, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.Images object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Images.class);
        long tableNativePtr = table.getNativePtr();
        ImagesColumnInfo columnInfo = (ImagesColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Images.class);
        long pkColumnIndex = columnInfo.idIndex;
        String primaryKeyValue = ((ImagesRealmProxyInterface) object).realmGet$id();
        long rowIndex = Table.NO_MATCH;
        if (primaryKeyValue == null) {
            rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
        } else {
            rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$url200_200 = ((ImagesRealmProxyInterface) object).realmGet$url200_200();
        if (realmGet$url200_200 != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.url200_200Index, rowIndex, realmGet$url200_200, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.url200_200Index, rowIndex, false);
        }
        String realmGet$url500_500 = ((ImagesRealmProxyInterface) object).realmGet$url500_500();
        if (realmGet$url500_500 != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.url500_500Index, rowIndex, realmGet$url500_500, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.url500_500Index, rowIndex, false);
        }
        String realmGet$url100_100 = ((ImagesRealmProxyInterface) object).realmGet$url100_100();
        if (realmGet$url100_100 != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.url100_100Index, rowIndex, realmGet$url100_100, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.url100_100Index, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.heightIndex, rowIndex, ((ImagesRealmProxyInterface) object).realmGet$height(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.widthIndex, rowIndex, ((ImagesRealmProxyInterface) object).realmGet$width(), false);
        String realmGet$mJson = ((ImagesRealmProxyInterface) object).realmGet$mJson();
        if (realmGet$mJson != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.mJsonIndex, rowIndex, realmGet$mJson, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.mJsonIndex, rowIndex, false);
        }
        String realmGet$hashtags = ((ImagesRealmProxyInterface) object).realmGet$hashtags();
        if (realmGet$hashtags != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.hashtagsIndex, rowIndex, realmGet$hashtags, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.hashtagsIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.typeIndex, rowIndex, ((ImagesRealmProxyInterface) object).realmGet$type(), false);
        String realmGet$urlFull = ((ImagesRealmProxyInterface) object).realmGet$urlFull();
        if (realmGet$urlFull != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.urlFullIndex, rowIndex, realmGet$urlFull, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.urlFullIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Images.class);
        long tableNativePtr = table.getNativePtr();
        ImagesColumnInfo columnInfo = (ImagesColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Images.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.directoriodelicias.apps.delicias.classes.Images object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.Images) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            String primaryKeyValue = ((ImagesRealmProxyInterface) object).realmGet$id();
            long rowIndex = Table.NO_MATCH;
            if (primaryKeyValue == null) {
                rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
            } else {
                rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$url200_200 = ((ImagesRealmProxyInterface) object).realmGet$url200_200();
            if (realmGet$url200_200 != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.url200_200Index, rowIndex, realmGet$url200_200, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.url200_200Index, rowIndex, false);
            }
            String realmGet$url500_500 = ((ImagesRealmProxyInterface) object).realmGet$url500_500();
            if (realmGet$url500_500 != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.url500_500Index, rowIndex, realmGet$url500_500, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.url500_500Index, rowIndex, false);
            }
            String realmGet$url100_100 = ((ImagesRealmProxyInterface) object).realmGet$url100_100();
            if (realmGet$url100_100 != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.url100_100Index, rowIndex, realmGet$url100_100, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.url100_100Index, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.heightIndex, rowIndex, ((ImagesRealmProxyInterface) object).realmGet$height(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.widthIndex, rowIndex, ((ImagesRealmProxyInterface) object).realmGet$width(), false);
            String realmGet$mJson = ((ImagesRealmProxyInterface) object).realmGet$mJson();
            if (realmGet$mJson != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.mJsonIndex, rowIndex, realmGet$mJson, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.mJsonIndex, rowIndex, false);
            }
            String realmGet$hashtags = ((ImagesRealmProxyInterface) object).realmGet$hashtags();
            if (realmGet$hashtags != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.hashtagsIndex, rowIndex, realmGet$hashtags, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.hashtagsIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.typeIndex, rowIndex, ((ImagesRealmProxyInterface) object).realmGet$type(), false);
            String realmGet$urlFull = ((ImagesRealmProxyInterface) object).realmGet$urlFull();
            if (realmGet$urlFull != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.urlFullIndex, rowIndex, realmGet$urlFull, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.urlFullIndex, rowIndex, false);
            }
        }
    }

    public static com.directoriodelicias.apps.delicias.classes.Images createDetachedCopy(com.directoriodelicias.apps.delicias.classes.Images realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.directoriodelicias.apps.delicias.classes.Images unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.directoriodelicias.apps.delicias.classes.Images();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.directoriodelicias.apps.delicias.classes.Images) cachedObject.object;
            }
            unmanagedObject = (com.directoriodelicias.apps.delicias.classes.Images) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        ImagesRealmProxyInterface unmanagedCopy = (ImagesRealmProxyInterface) unmanagedObject;
        ImagesRealmProxyInterface realmSource = (ImagesRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$url200_200(realmSource.realmGet$url200_200());
        unmanagedCopy.realmSet$url500_500(realmSource.realmGet$url500_500());
        unmanagedCopy.realmSet$url100_100(realmSource.realmGet$url100_100());
        unmanagedCopy.realmSet$height(realmSource.realmGet$height());
        unmanagedCopy.realmSet$width(realmSource.realmGet$width());
        unmanagedCopy.realmSet$mJson(realmSource.realmGet$mJson());
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());
        unmanagedCopy.realmSet$hashtags(realmSource.realmGet$hashtags());
        unmanagedCopy.realmSet$type(realmSource.realmGet$type());
        unmanagedCopy.realmSet$urlFull(realmSource.realmGet$urlFull());

        return unmanagedObject;
    }

    static com.directoriodelicias.apps.delicias.classes.Images update(Realm realm, com.directoriodelicias.apps.delicias.classes.Images realmObject, com.directoriodelicias.apps.delicias.classes.Images newObject, Map<RealmModel, RealmObjectProxy> cache) {
        ImagesRealmProxyInterface realmObjectTarget = (ImagesRealmProxyInterface) realmObject;
        ImagesRealmProxyInterface realmObjectSource = (ImagesRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$url200_200(realmObjectSource.realmGet$url200_200());
        realmObjectTarget.realmSet$url500_500(realmObjectSource.realmGet$url500_500());
        realmObjectTarget.realmSet$url100_100(realmObjectSource.realmGet$url100_100());
        realmObjectTarget.realmSet$height(realmObjectSource.realmGet$height());
        realmObjectTarget.realmSet$width(realmObjectSource.realmGet$width());
        realmObjectTarget.realmSet$mJson(realmObjectSource.realmGet$mJson());
        realmObjectTarget.realmSet$hashtags(realmObjectSource.realmGet$hashtags());
        realmObjectTarget.realmSet$type(realmObjectSource.realmGet$type());
        realmObjectTarget.realmSet$urlFull(realmObjectSource.realmGet$urlFull());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Images = proxy[");
        stringBuilder.append("{url200_200:");
        stringBuilder.append(realmGet$url200_200() != null ? realmGet$url200_200() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{url500_500:");
        stringBuilder.append(realmGet$url500_500() != null ? realmGet$url500_500() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{url100_100:");
        stringBuilder.append(realmGet$url100_100() != null ? realmGet$url100_100() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{height:");
        stringBuilder.append(realmGet$height());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{width:");
        stringBuilder.append(realmGet$width());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{mJson:");
        stringBuilder.append(realmGet$mJson() != null ? realmGet$mJson() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id() != null ? realmGet$id() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{hashtags:");
        stringBuilder.append(realmGet$hashtags() != null ? realmGet$hashtags() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{type:");
        stringBuilder.append(realmGet$type());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{urlFull:");
        stringBuilder.append(realmGet$urlFull() != null ? realmGet$urlFull() : "null");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImagesRealmProxy aImages = (ImagesRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aImages.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aImages.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aImages.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
