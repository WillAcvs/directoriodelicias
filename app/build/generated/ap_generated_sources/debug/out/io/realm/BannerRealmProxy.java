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
public class BannerRealmProxy extends com.directoriodelicias.apps.delicias.classes.Banner
    implements RealmObjectProxy, BannerRealmProxyInterface {

    static final class BannerColumnInfo extends ColumnInfo {
        long idIndex;
        long titleIndex;
        long descriptionIndex;
        long imagesIndex;
        long moduleIndex;
        long module_idIndex;
        long statusIndex;
        long date_startIndex;
        long date_endIndex;
        long is_can_expireIndex;
        long listImagesIndex;

        BannerColumnInfo(OsSchemaInfo schemaInfo) {
            super(11);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("Banner");
            this.idIndex = addColumnDetails("id", objectSchemaInfo);
            this.titleIndex = addColumnDetails("title", objectSchemaInfo);
            this.descriptionIndex = addColumnDetails("description", objectSchemaInfo);
            this.imagesIndex = addColumnDetails("images", objectSchemaInfo);
            this.moduleIndex = addColumnDetails("module", objectSchemaInfo);
            this.module_idIndex = addColumnDetails("module_id", objectSchemaInfo);
            this.statusIndex = addColumnDetails("status", objectSchemaInfo);
            this.date_startIndex = addColumnDetails("date_start", objectSchemaInfo);
            this.date_endIndex = addColumnDetails("date_end", objectSchemaInfo);
            this.is_can_expireIndex = addColumnDetails("is_can_expire", objectSchemaInfo);
            this.listImagesIndex = addColumnDetails("listImages", objectSchemaInfo);
        }

        BannerColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new BannerColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final BannerColumnInfo src = (BannerColumnInfo) rawSrc;
            final BannerColumnInfo dst = (BannerColumnInfo) rawDst;
            dst.idIndex = src.idIndex;
            dst.titleIndex = src.titleIndex;
            dst.descriptionIndex = src.descriptionIndex;
            dst.imagesIndex = src.imagesIndex;
            dst.moduleIndex = src.moduleIndex;
            dst.module_idIndex = src.module_idIndex;
            dst.statusIndex = src.statusIndex;
            dst.date_startIndex = src.date_startIndex;
            dst.date_endIndex = src.date_endIndex;
            dst.is_can_expireIndex = src.is_can_expireIndex;
            dst.listImagesIndex = src.listImagesIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>(11);
        fieldNames.add("id");
        fieldNames.add("title");
        fieldNames.add("description");
        fieldNames.add("images");
        fieldNames.add("module");
        fieldNames.add("module_id");
        fieldNames.add("status");
        fieldNames.add("date_start");
        fieldNames.add("date_end");
        fieldNames.add("is_can_expire");
        fieldNames.add("listImages");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private BannerColumnInfo columnInfo;
    private ProxyState<com.directoriodelicias.apps.delicias.classes.Banner> proxyState;
    private RealmList<com.directoriodelicias.apps.delicias.classes.Images> listImagesRealmList;

    BannerRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (BannerColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.directoriodelicias.apps.delicias.classes.Banner>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.idIndex);
    }

    @Override
    public void realmSet$id(int value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'id' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$title() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.titleIndex);
    }

    @Override
    public void realmSet$title(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.titleIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.titleIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.titleIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.titleIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$description() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.descriptionIndex);
    }

    @Override
    public void realmSet$description(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.descriptionIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.descriptionIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.descriptionIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.descriptionIndex, value);
    }

    @Override
    public com.directoriodelicias.apps.delicias.classes.Images realmGet$images() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNullLink(columnInfo.imagesIndex)) {
            return null;
        }
        return proxyState.getRealm$realm().get(com.directoriodelicias.apps.delicias.classes.Images.class, proxyState.getRow$realm().getLink(columnInfo.imagesIndex), false, Collections.<String>emptyList());
    }

    @Override
    public void realmSet$images(com.directoriodelicias.apps.delicias.classes.Images value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("images")) {
                return;
            }
            if (value != null && !RealmObject.isManaged(value)) {
                value = ((Realm) proxyState.getRealm$realm()).copyToRealm(value);
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                // Table#nullifyLink() does not support default value. Just using Row.
                row.nullifyLink(columnInfo.imagesIndex);
                return;
            }
            proxyState.checkValidObject(value);
            row.getTable().setLink(columnInfo.imagesIndex, row.getIndex(), ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().nullifyLink(columnInfo.imagesIndex);
            return;
        }
        proxyState.checkValidObject(value);
        proxyState.getRow$realm().setLink(columnInfo.imagesIndex, ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$module() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.moduleIndex);
    }

    @Override
    public void realmSet$module(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.moduleIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.moduleIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.moduleIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.moduleIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$module_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.module_idIndex);
    }

    @Override
    public void realmSet$module_id(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.module_idIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.module_idIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.module_idIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.module_idIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$status() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.statusIndex);
    }

    @Override
    public void realmSet$status(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.statusIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.statusIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$date_start() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.date_startIndex);
    }

    @Override
    public void realmSet$date_start(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.date_startIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.date_startIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.date_startIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.date_startIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$date_end() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.date_endIndex);
    }

    @Override
    public void realmSet$date_end(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.date_endIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.date_endIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.date_endIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.date_endIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$is_can_expire() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.is_can_expireIndex);
    }

    @Override
    public void realmSet$is_can_expire(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.is_can_expireIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.is_can_expireIndex, value);
    }

    @Override
    public RealmList<com.directoriodelicias.apps.delicias.classes.Images> realmGet$listImages() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (listImagesRealmList != null) {
            return listImagesRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.listImagesIndex);
            listImagesRealmList = new RealmList<com.directoriodelicias.apps.delicias.classes.Images>(com.directoriodelicias.apps.delicias.classes.Images.class, osList, proxyState.getRealm$realm());
            return listImagesRealmList;
        }
    }

    @Override
    public void realmSet$listImages(RealmList<com.directoriodelicias.apps.delicias.classes.Images> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("listImages")) {
                return;
            }
            // if the list contains unmanaged RealmObjects, convert them to managed.
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.directoriodelicias.apps.delicias.classes.Images> original = value;
                value = new RealmList<com.directoriodelicias.apps.delicias.classes.Images>();
                for (com.directoriodelicias.apps.delicias.classes.Images item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.listImagesIndex);
        // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
        if (value != null && value.size() == osList.size()) {
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.directoriodelicias.apps.delicias.classes.Images linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.setRow(i, ((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        } else {
            osList.removeAll();
            if (value == null) {
                return;
            }
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.directoriodelicias.apps.delicias.classes.Images linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.addRow(((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        }
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("Banner", 11, 0);
        builder.addPersistedProperty("id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("title", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("description", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedLinkProperty("images", RealmFieldType.OBJECT, "Images");
        builder.addPersistedProperty("module", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("module_id", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("status", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("date_start", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("date_end", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("is_can_expire", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedLinkProperty("listImages", RealmFieldType.LIST, "Images");
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static BannerColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new BannerColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "Banner";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.directoriodelicias.apps.delicias.classes.Banner createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(2);
        com.directoriodelicias.apps.delicias.classes.Banner obj = null;
        if (update) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Banner.class);
            BannerColumnInfo columnInfo = (BannerColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Banner.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("id")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Banner.class), false, Collections.<String> emptyList());
                    obj = new io.realm.BannerRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("images")) {
                excludeFields.add("images");
            }
            if (json.has("listImages")) {
                excludeFields.add("listImages");
            }
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.BannerRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Banner.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.BannerRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Banner.class, json.getInt("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }

        final BannerRealmProxyInterface objProxy = (BannerRealmProxyInterface) obj;
        if (json.has("title")) {
            if (json.isNull("title")) {
                objProxy.realmSet$title(null);
            } else {
                objProxy.realmSet$title((String) json.getString("title"));
            }
        }
        if (json.has("description")) {
            if (json.isNull("description")) {
                objProxy.realmSet$description(null);
            } else {
                objProxy.realmSet$description((String) json.getString("description"));
            }
        }
        if (json.has("images")) {
            if (json.isNull("images")) {
                objProxy.realmSet$images(null);
            } else {
                com.directoriodelicias.apps.delicias.classes.Images imagesObj = ImagesRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("images"), update);
                objProxy.realmSet$images(imagesObj);
            }
        }
        if (json.has("module")) {
            if (json.isNull("module")) {
                objProxy.realmSet$module(null);
            } else {
                objProxy.realmSet$module((String) json.getString("module"));
            }
        }
        if (json.has("module_id")) {
            if (json.isNull("module_id")) {
                objProxy.realmSet$module_id(null);
            } else {
                objProxy.realmSet$module_id((String) json.getString("module_id"));
            }
        }
        if (json.has("status")) {
            if (json.isNull("status")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'status' to null.");
            } else {
                objProxy.realmSet$status((int) json.getInt("status"));
            }
        }
        if (json.has("date_start")) {
            if (json.isNull("date_start")) {
                objProxy.realmSet$date_start(null);
            } else {
                objProxy.realmSet$date_start((String) json.getString("date_start"));
            }
        }
        if (json.has("date_end")) {
            if (json.isNull("date_end")) {
                objProxy.realmSet$date_end(null);
            } else {
                objProxy.realmSet$date_end((String) json.getString("date_end"));
            }
        }
        if (json.has("is_can_expire")) {
            if (json.isNull("is_can_expire")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'is_can_expire' to null.");
            } else {
                objProxy.realmSet$is_can_expire((int) json.getInt("is_can_expire"));
            }
        }
        if (json.has("listImages")) {
            if (json.isNull("listImages")) {
                objProxy.realmSet$listImages(null);
            } else {
                objProxy.realmGet$listImages().clear();
                JSONArray array = json.getJSONArray("listImages");
                for (int i = 0; i < array.length(); i++) {
                    com.directoriodelicias.apps.delicias.classes.Images item = ImagesRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$listImages().add(item);
                }
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.directoriodelicias.apps.delicias.classes.Banner createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.directoriodelicias.apps.delicias.classes.Banner obj = new com.directoriodelicias.apps.delicias.classes.Banner();
        final BannerRealmProxyInterface objProxy = (BannerRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'id' to null.");
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("title")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$title((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$title(null);
                }
            } else if (name.equals("description")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$description((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$description(null);
                }
            } else if (name.equals("images")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$images(null);
                } else {
                    com.directoriodelicias.apps.delicias.classes.Images imagesObj = ImagesRealmProxy.createUsingJsonStream(realm, reader);
                    objProxy.realmSet$images(imagesObj);
                }
            } else if (name.equals("module")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$module((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$module(null);
                }
            } else if (name.equals("module_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$module_id((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$module_id(null);
                }
            } else if (name.equals("status")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$status((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'status' to null.");
                }
            } else if (name.equals("date_start")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$date_start((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$date_start(null);
                }
            } else if (name.equals("date_end")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$date_end((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$date_end(null);
                }
            } else if (name.equals("is_can_expire")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$is_can_expire((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'is_can_expire' to null.");
                }
            } else if (name.equals("listImages")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$listImages(null);
                } else {
                    objProxy.realmSet$listImages(new RealmList<com.directoriodelicias.apps.delicias.classes.Images>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.directoriodelicias.apps.delicias.classes.Images item = ImagesRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$listImages().add(item);
                    }
                    reader.endArray();
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

    public static com.directoriodelicias.apps.delicias.classes.Banner copyOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.Banner object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.directoriodelicias.apps.delicias.classes.Banner) cachedRealmObject;
        }

        com.directoriodelicias.apps.delicias.classes.Banner realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Banner.class);
            BannerColumnInfo columnInfo = (BannerColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Banner.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = table.findFirstLong(pkColumnIndex, ((BannerRealmProxyInterface) object).realmGet$id());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Banner.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.BannerRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.directoriodelicias.apps.delicias.classes.Banner copy(Realm realm, com.directoriodelicias.apps.delicias.classes.Banner newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.directoriodelicias.apps.delicias.classes.Banner) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.directoriodelicias.apps.delicias.classes.Banner realmObject = realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Banner.class, ((BannerRealmProxyInterface) newObject).realmGet$id(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        BannerRealmProxyInterface realmObjectSource = (BannerRealmProxyInterface) newObject;
        BannerRealmProxyInterface realmObjectCopy = (BannerRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$title(realmObjectSource.realmGet$title());
        realmObjectCopy.realmSet$description(realmObjectSource.realmGet$description());

        com.directoriodelicias.apps.delicias.classes.Images imagesObj = realmObjectSource.realmGet$images();
        if (imagesObj == null) {
            realmObjectCopy.realmSet$images(null);
        } else {
            com.directoriodelicias.apps.delicias.classes.Images cacheimages = (com.directoriodelicias.apps.delicias.classes.Images) cache.get(imagesObj);
            if (cacheimages != null) {
                realmObjectCopy.realmSet$images(cacheimages);
            } else {
                realmObjectCopy.realmSet$images(ImagesRealmProxy.copyOrUpdate(realm, imagesObj, update, cache));
            }
        }
        realmObjectCopy.realmSet$module(realmObjectSource.realmGet$module());
        realmObjectCopy.realmSet$module_id(realmObjectSource.realmGet$module_id());
        realmObjectCopy.realmSet$status(realmObjectSource.realmGet$status());
        realmObjectCopy.realmSet$date_start(realmObjectSource.realmGet$date_start());
        realmObjectCopy.realmSet$date_end(realmObjectSource.realmGet$date_end());
        realmObjectCopy.realmSet$is_can_expire(realmObjectSource.realmGet$is_can_expire());

        RealmList<com.directoriodelicias.apps.delicias.classes.Images> listImagesList = realmObjectSource.realmGet$listImages();
        if (listImagesList != null) {
            RealmList<com.directoriodelicias.apps.delicias.classes.Images> listImagesRealmList = realmObjectCopy.realmGet$listImages();
            listImagesRealmList.clear();
            for (int i = 0; i < listImagesList.size(); i++) {
                com.directoriodelicias.apps.delicias.classes.Images listImagesItem = listImagesList.get(i);
                com.directoriodelicias.apps.delicias.classes.Images cachelistImages = (com.directoriodelicias.apps.delicias.classes.Images) cache.get(listImagesItem);
                if (cachelistImages != null) {
                    listImagesRealmList.add(cachelistImages);
                } else {
                    listImagesRealmList.add(ImagesRealmProxy.copyOrUpdate(realm, listImagesItem, update, cache));
                }
            }
        }

        return realmObject;
    }

    public static long insert(Realm realm, com.directoriodelicias.apps.delicias.classes.Banner object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Banner.class);
        long tableNativePtr = table.getNativePtr();
        BannerColumnInfo columnInfo = (BannerColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Banner.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((BannerRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((BannerRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((BannerRealmProxyInterface) object).realmGet$id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$title = ((BannerRealmProxyInterface) object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
        }
        String realmGet$description = ((BannerRealmProxyInterface) object).realmGet$description();
        if (realmGet$description != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
        }

        com.directoriodelicias.apps.delicias.classes.Images imagesObj = ((BannerRealmProxyInterface) object).realmGet$images();
        if (imagesObj != null) {
            Long cacheimages = cache.get(imagesObj);
            if (cacheimages == null) {
                cacheimages = ImagesRealmProxy.insert(realm, imagesObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.imagesIndex, rowIndex, cacheimages, false);
        }
        String realmGet$module = ((BannerRealmProxyInterface) object).realmGet$module();
        if (realmGet$module != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.moduleIndex, rowIndex, realmGet$module, false);
        }
        String realmGet$module_id = ((BannerRealmProxyInterface) object).realmGet$module_id();
        if (realmGet$module_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.module_idIndex, rowIndex, realmGet$module_id, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((BannerRealmProxyInterface) object).realmGet$status(), false);
        String realmGet$date_start = ((BannerRealmProxyInterface) object).realmGet$date_start();
        if (realmGet$date_start != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.date_startIndex, rowIndex, realmGet$date_start, false);
        }
        String realmGet$date_end = ((BannerRealmProxyInterface) object).realmGet$date_end();
        if (realmGet$date_end != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.date_endIndex, rowIndex, realmGet$date_end, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.is_can_expireIndex, rowIndex, ((BannerRealmProxyInterface) object).realmGet$is_can_expire(), false);

        RealmList<com.directoriodelicias.apps.delicias.classes.Images> listImagesList = ((BannerRealmProxyInterface) object).realmGet$listImages();
        if (listImagesList != null) {
            OsList listImagesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.listImagesIndex);
            for (com.directoriodelicias.apps.delicias.classes.Images listImagesItem : listImagesList) {
                Long cacheItemIndexlistImages = cache.get(listImagesItem);
                if (cacheItemIndexlistImages == null) {
                    cacheItemIndexlistImages = ImagesRealmProxy.insert(realm, listImagesItem, cache);
                }
                listImagesOsList.addRow(cacheItemIndexlistImages);
            }
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Banner.class);
        long tableNativePtr = table.getNativePtr();
        BannerColumnInfo columnInfo = (BannerColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Banner.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.directoriodelicias.apps.delicias.classes.Banner object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.Banner) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((BannerRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((BannerRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((BannerRealmProxyInterface) object).realmGet$id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$title = ((BannerRealmProxyInterface) object).realmGet$title();
            if (realmGet$title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
            }
            String realmGet$description = ((BannerRealmProxyInterface) object).realmGet$description();
            if (realmGet$description != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
            }

            com.directoriodelicias.apps.delicias.classes.Images imagesObj = ((BannerRealmProxyInterface) object).realmGet$images();
            if (imagesObj != null) {
                Long cacheimages = cache.get(imagesObj);
                if (cacheimages == null) {
                    cacheimages = ImagesRealmProxy.insert(realm, imagesObj, cache);
                }
                table.setLink(columnInfo.imagesIndex, rowIndex, cacheimages, false);
            }
            String realmGet$module = ((BannerRealmProxyInterface) object).realmGet$module();
            if (realmGet$module != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.moduleIndex, rowIndex, realmGet$module, false);
            }
            String realmGet$module_id = ((BannerRealmProxyInterface) object).realmGet$module_id();
            if (realmGet$module_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.module_idIndex, rowIndex, realmGet$module_id, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((BannerRealmProxyInterface) object).realmGet$status(), false);
            String realmGet$date_start = ((BannerRealmProxyInterface) object).realmGet$date_start();
            if (realmGet$date_start != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.date_startIndex, rowIndex, realmGet$date_start, false);
            }
            String realmGet$date_end = ((BannerRealmProxyInterface) object).realmGet$date_end();
            if (realmGet$date_end != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.date_endIndex, rowIndex, realmGet$date_end, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.is_can_expireIndex, rowIndex, ((BannerRealmProxyInterface) object).realmGet$is_can_expire(), false);

            RealmList<com.directoriodelicias.apps.delicias.classes.Images> listImagesList = ((BannerRealmProxyInterface) object).realmGet$listImages();
            if (listImagesList != null) {
                OsList listImagesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.listImagesIndex);
                for (com.directoriodelicias.apps.delicias.classes.Images listImagesItem : listImagesList) {
                    Long cacheItemIndexlistImages = cache.get(listImagesItem);
                    if (cacheItemIndexlistImages == null) {
                        cacheItemIndexlistImages = ImagesRealmProxy.insert(realm, listImagesItem, cache);
                    }
                    listImagesOsList.addRow(cacheItemIndexlistImages);
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.Banner object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Banner.class);
        long tableNativePtr = table.getNativePtr();
        BannerColumnInfo columnInfo = (BannerColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Banner.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((BannerRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((BannerRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((BannerRealmProxyInterface) object).realmGet$id());
        }
        cache.put(object, rowIndex);
        String realmGet$title = ((BannerRealmProxyInterface) object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.titleIndex, rowIndex, false);
        }
        String realmGet$description = ((BannerRealmProxyInterface) object).realmGet$description();
        if (realmGet$description != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.descriptionIndex, rowIndex, false);
        }

        com.directoriodelicias.apps.delicias.classes.Images imagesObj = ((BannerRealmProxyInterface) object).realmGet$images();
        if (imagesObj != null) {
            Long cacheimages = cache.get(imagesObj);
            if (cacheimages == null) {
                cacheimages = ImagesRealmProxy.insertOrUpdate(realm, imagesObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.imagesIndex, rowIndex, cacheimages, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.imagesIndex, rowIndex);
        }
        String realmGet$module = ((BannerRealmProxyInterface) object).realmGet$module();
        if (realmGet$module != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.moduleIndex, rowIndex, realmGet$module, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.moduleIndex, rowIndex, false);
        }
        String realmGet$module_id = ((BannerRealmProxyInterface) object).realmGet$module_id();
        if (realmGet$module_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.module_idIndex, rowIndex, realmGet$module_id, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.module_idIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((BannerRealmProxyInterface) object).realmGet$status(), false);
        String realmGet$date_start = ((BannerRealmProxyInterface) object).realmGet$date_start();
        if (realmGet$date_start != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.date_startIndex, rowIndex, realmGet$date_start, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.date_startIndex, rowIndex, false);
        }
        String realmGet$date_end = ((BannerRealmProxyInterface) object).realmGet$date_end();
        if (realmGet$date_end != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.date_endIndex, rowIndex, realmGet$date_end, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.date_endIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.is_can_expireIndex, rowIndex, ((BannerRealmProxyInterface) object).realmGet$is_can_expire(), false);

        OsList listImagesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.listImagesIndex);
        RealmList<com.directoriodelicias.apps.delicias.classes.Images> listImagesList = ((BannerRealmProxyInterface) object).realmGet$listImages();
        if (listImagesList != null && listImagesList.size() == listImagesOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = listImagesList.size();
            for (int i = 0; i < objects; i++) {
                com.directoriodelicias.apps.delicias.classes.Images listImagesItem = listImagesList.get(i);
                Long cacheItemIndexlistImages = cache.get(listImagesItem);
                if (cacheItemIndexlistImages == null) {
                    cacheItemIndexlistImages = ImagesRealmProxy.insertOrUpdate(realm, listImagesItem, cache);
                }
                listImagesOsList.setRow(i, cacheItemIndexlistImages);
            }
        } else {
            listImagesOsList.removeAll();
            if (listImagesList != null) {
                for (com.directoriodelicias.apps.delicias.classes.Images listImagesItem : listImagesList) {
                    Long cacheItemIndexlistImages = cache.get(listImagesItem);
                    if (cacheItemIndexlistImages == null) {
                        cacheItemIndexlistImages = ImagesRealmProxy.insertOrUpdate(realm, listImagesItem, cache);
                    }
                    listImagesOsList.addRow(cacheItemIndexlistImages);
                }
            }
        }

        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Banner.class);
        long tableNativePtr = table.getNativePtr();
        BannerColumnInfo columnInfo = (BannerColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Banner.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.directoriodelicias.apps.delicias.classes.Banner object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.Banner) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((BannerRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((BannerRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((BannerRealmProxyInterface) object).realmGet$id());
            }
            cache.put(object, rowIndex);
            String realmGet$title = ((BannerRealmProxyInterface) object).realmGet$title();
            if (realmGet$title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.titleIndex, rowIndex, false);
            }
            String realmGet$description = ((BannerRealmProxyInterface) object).realmGet$description();
            if (realmGet$description != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.descriptionIndex, rowIndex, false);
            }

            com.directoriodelicias.apps.delicias.classes.Images imagesObj = ((BannerRealmProxyInterface) object).realmGet$images();
            if (imagesObj != null) {
                Long cacheimages = cache.get(imagesObj);
                if (cacheimages == null) {
                    cacheimages = ImagesRealmProxy.insertOrUpdate(realm, imagesObj, cache);
                }
                Table.nativeSetLink(tableNativePtr, columnInfo.imagesIndex, rowIndex, cacheimages, false);
            } else {
                Table.nativeNullifyLink(tableNativePtr, columnInfo.imagesIndex, rowIndex);
            }
            String realmGet$module = ((BannerRealmProxyInterface) object).realmGet$module();
            if (realmGet$module != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.moduleIndex, rowIndex, realmGet$module, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.moduleIndex, rowIndex, false);
            }
            String realmGet$module_id = ((BannerRealmProxyInterface) object).realmGet$module_id();
            if (realmGet$module_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.module_idIndex, rowIndex, realmGet$module_id, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.module_idIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((BannerRealmProxyInterface) object).realmGet$status(), false);
            String realmGet$date_start = ((BannerRealmProxyInterface) object).realmGet$date_start();
            if (realmGet$date_start != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.date_startIndex, rowIndex, realmGet$date_start, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.date_startIndex, rowIndex, false);
            }
            String realmGet$date_end = ((BannerRealmProxyInterface) object).realmGet$date_end();
            if (realmGet$date_end != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.date_endIndex, rowIndex, realmGet$date_end, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.date_endIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.is_can_expireIndex, rowIndex, ((BannerRealmProxyInterface) object).realmGet$is_can_expire(), false);

            OsList listImagesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.listImagesIndex);
            RealmList<com.directoriodelicias.apps.delicias.classes.Images> listImagesList = ((BannerRealmProxyInterface) object).realmGet$listImages();
            if (listImagesList != null && listImagesList.size() == listImagesOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = listImagesList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.directoriodelicias.apps.delicias.classes.Images listImagesItem = listImagesList.get(i);
                    Long cacheItemIndexlistImages = cache.get(listImagesItem);
                    if (cacheItemIndexlistImages == null) {
                        cacheItemIndexlistImages = ImagesRealmProxy.insertOrUpdate(realm, listImagesItem, cache);
                    }
                    listImagesOsList.setRow(i, cacheItemIndexlistImages);
                }
            } else {
                listImagesOsList.removeAll();
                if (listImagesList != null) {
                    for (com.directoriodelicias.apps.delicias.classes.Images listImagesItem : listImagesList) {
                        Long cacheItemIndexlistImages = cache.get(listImagesItem);
                        if (cacheItemIndexlistImages == null) {
                            cacheItemIndexlistImages = ImagesRealmProxy.insertOrUpdate(realm, listImagesItem, cache);
                        }
                        listImagesOsList.addRow(cacheItemIndexlistImages);
                    }
                }
            }

        }
    }

    public static com.directoriodelicias.apps.delicias.classes.Banner createDetachedCopy(com.directoriodelicias.apps.delicias.classes.Banner realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.directoriodelicias.apps.delicias.classes.Banner unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.directoriodelicias.apps.delicias.classes.Banner();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.directoriodelicias.apps.delicias.classes.Banner) cachedObject.object;
            }
            unmanagedObject = (com.directoriodelicias.apps.delicias.classes.Banner) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        BannerRealmProxyInterface unmanagedCopy = (BannerRealmProxyInterface) unmanagedObject;
        BannerRealmProxyInterface realmSource = (BannerRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());
        unmanagedCopy.realmSet$title(realmSource.realmGet$title());
        unmanagedCopy.realmSet$description(realmSource.realmGet$description());

        // Deep copy of images
        unmanagedCopy.realmSet$images(ImagesRealmProxy.createDetachedCopy(realmSource.realmGet$images(), currentDepth + 1, maxDepth, cache));
        unmanagedCopy.realmSet$module(realmSource.realmGet$module());
        unmanagedCopy.realmSet$module_id(realmSource.realmGet$module_id());
        unmanagedCopy.realmSet$status(realmSource.realmGet$status());
        unmanagedCopy.realmSet$date_start(realmSource.realmGet$date_start());
        unmanagedCopy.realmSet$date_end(realmSource.realmGet$date_end());
        unmanagedCopy.realmSet$is_can_expire(realmSource.realmGet$is_can_expire());

        // Deep copy of listImages
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$listImages(null);
        } else {
            RealmList<com.directoriodelicias.apps.delicias.classes.Images> managedlistImagesList = realmSource.realmGet$listImages();
            RealmList<com.directoriodelicias.apps.delicias.classes.Images> unmanagedlistImagesList = new RealmList<com.directoriodelicias.apps.delicias.classes.Images>();
            unmanagedCopy.realmSet$listImages(unmanagedlistImagesList);
            int nextDepth = currentDepth + 1;
            int size = managedlistImagesList.size();
            for (int i = 0; i < size; i++) {
                com.directoriodelicias.apps.delicias.classes.Images item = ImagesRealmProxy.createDetachedCopy(managedlistImagesList.get(i), nextDepth, maxDepth, cache);
                unmanagedlistImagesList.add(item);
            }
        }

        return unmanagedObject;
    }

    static com.directoriodelicias.apps.delicias.classes.Banner update(Realm realm, com.directoriodelicias.apps.delicias.classes.Banner realmObject, com.directoriodelicias.apps.delicias.classes.Banner newObject, Map<RealmModel, RealmObjectProxy> cache) {
        BannerRealmProxyInterface realmObjectTarget = (BannerRealmProxyInterface) realmObject;
        BannerRealmProxyInterface realmObjectSource = (BannerRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$title(realmObjectSource.realmGet$title());
        realmObjectTarget.realmSet$description(realmObjectSource.realmGet$description());
        com.directoriodelicias.apps.delicias.classes.Images imagesObj = realmObjectSource.realmGet$images();
        if (imagesObj == null) {
            realmObjectTarget.realmSet$images(null);
        } else {
            com.directoriodelicias.apps.delicias.classes.Images cacheimages = (com.directoriodelicias.apps.delicias.classes.Images) cache.get(imagesObj);
            if (cacheimages != null) {
                realmObjectTarget.realmSet$images(cacheimages);
            } else {
                realmObjectTarget.realmSet$images(ImagesRealmProxy.copyOrUpdate(realm, imagesObj, true, cache));
            }
        }
        realmObjectTarget.realmSet$module(realmObjectSource.realmGet$module());
        realmObjectTarget.realmSet$module_id(realmObjectSource.realmGet$module_id());
        realmObjectTarget.realmSet$status(realmObjectSource.realmGet$status());
        realmObjectTarget.realmSet$date_start(realmObjectSource.realmGet$date_start());
        realmObjectTarget.realmSet$date_end(realmObjectSource.realmGet$date_end());
        realmObjectTarget.realmSet$is_can_expire(realmObjectSource.realmGet$is_can_expire());
        RealmList<com.directoriodelicias.apps.delicias.classes.Images> listImagesList = realmObjectSource.realmGet$listImages();
        RealmList<com.directoriodelicias.apps.delicias.classes.Images> listImagesRealmList = realmObjectTarget.realmGet$listImages();
        if (listImagesList != null && listImagesList.size() == listImagesRealmList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = listImagesList.size();
            for (int i = 0; i < objects; i++) {
                com.directoriodelicias.apps.delicias.classes.Images listImagesItem = listImagesList.get(i);
                com.directoriodelicias.apps.delicias.classes.Images cachelistImages = (com.directoriodelicias.apps.delicias.classes.Images) cache.get(listImagesItem);
                if (cachelistImages != null) {
                    listImagesRealmList.set(i, cachelistImages);
                } else {
                    listImagesRealmList.set(i, ImagesRealmProxy.copyOrUpdate(realm, listImagesItem, true, cache));
                }
            }
        } else {
            listImagesRealmList.clear();
            if (listImagesList != null) {
                for (int i = 0; i < listImagesList.size(); i++) {
                    com.directoriodelicias.apps.delicias.classes.Images listImagesItem = listImagesList.get(i);
                    com.directoriodelicias.apps.delicias.classes.Images cachelistImages = (com.directoriodelicias.apps.delicias.classes.Images) cache.get(listImagesItem);
                    if (cachelistImages != null) {
                        listImagesRealmList.add(cachelistImages);
                    } else {
                        listImagesRealmList.add(ImagesRealmProxy.copyOrUpdate(realm, listImagesItem, true, cache));
                    }
                }
            }
        }
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Banner = proxy[");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{title:");
        stringBuilder.append(realmGet$title() != null ? realmGet$title() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{description:");
        stringBuilder.append(realmGet$description() != null ? realmGet$description() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{images:");
        stringBuilder.append(realmGet$images() != null ? "Images" : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{module:");
        stringBuilder.append(realmGet$module() != null ? realmGet$module() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{module_id:");
        stringBuilder.append(realmGet$module_id() != null ? realmGet$module_id() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{status:");
        stringBuilder.append(realmGet$status());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{date_start:");
        stringBuilder.append(realmGet$date_start() != null ? realmGet$date_start() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{date_end:");
        stringBuilder.append(realmGet$date_end() != null ? realmGet$date_end() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{is_can_expire:");
        stringBuilder.append(realmGet$is_can_expire());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{listImages:");
        stringBuilder.append("RealmList<Images>[").append(realmGet$listImages().size()).append("]");
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
        BannerRealmProxy aBanner = (BannerRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aBanner.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aBanner.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aBanner.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
