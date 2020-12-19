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
public class NotificationRealmProxy extends com.directoriodelicias.apps.delicias.classes.Notification
    implements RealmObjectProxy, NotificationRealmProxyInterface {

    static final class NotificationColumnInfo extends ColumnInfo {
        long idIndex;
        long labelIndex;
        long label_descriptionIndex;
        long auth_typeIndex;
        long imageIndex;
        long imagesIndex;
        long auth_idIndex;
        long moduleIndex;
        long module_idIndex;
        long detailIndex;
        long statusIndex;
        long updated_atIndex;
        long created_atIndex;
        long compaigns_idIndex;

        NotificationColumnInfo(OsSchemaInfo schemaInfo) {
            super(14);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("Notification");
            this.idIndex = addColumnDetails("id", objectSchemaInfo);
            this.labelIndex = addColumnDetails("label", objectSchemaInfo);
            this.label_descriptionIndex = addColumnDetails("label_description", objectSchemaInfo);
            this.auth_typeIndex = addColumnDetails("auth_type", objectSchemaInfo);
            this.imageIndex = addColumnDetails("image", objectSchemaInfo);
            this.imagesIndex = addColumnDetails("images", objectSchemaInfo);
            this.auth_idIndex = addColumnDetails("auth_id", objectSchemaInfo);
            this.moduleIndex = addColumnDetails("module", objectSchemaInfo);
            this.module_idIndex = addColumnDetails("module_id", objectSchemaInfo);
            this.detailIndex = addColumnDetails("detail", objectSchemaInfo);
            this.statusIndex = addColumnDetails("status", objectSchemaInfo);
            this.updated_atIndex = addColumnDetails("updated_at", objectSchemaInfo);
            this.created_atIndex = addColumnDetails("created_at", objectSchemaInfo);
            this.compaigns_idIndex = addColumnDetails("compaigns_id", objectSchemaInfo);
        }

        NotificationColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new NotificationColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final NotificationColumnInfo src = (NotificationColumnInfo) rawSrc;
            final NotificationColumnInfo dst = (NotificationColumnInfo) rawDst;
            dst.idIndex = src.idIndex;
            dst.labelIndex = src.labelIndex;
            dst.label_descriptionIndex = src.label_descriptionIndex;
            dst.auth_typeIndex = src.auth_typeIndex;
            dst.imageIndex = src.imageIndex;
            dst.imagesIndex = src.imagesIndex;
            dst.auth_idIndex = src.auth_idIndex;
            dst.moduleIndex = src.moduleIndex;
            dst.module_idIndex = src.module_idIndex;
            dst.detailIndex = src.detailIndex;
            dst.statusIndex = src.statusIndex;
            dst.updated_atIndex = src.updated_atIndex;
            dst.created_atIndex = src.created_atIndex;
            dst.compaigns_idIndex = src.compaigns_idIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>(14);
        fieldNames.add("id");
        fieldNames.add("label");
        fieldNames.add("label_description");
        fieldNames.add("auth_type");
        fieldNames.add("image");
        fieldNames.add("images");
        fieldNames.add("auth_id");
        fieldNames.add("module");
        fieldNames.add("module_id");
        fieldNames.add("detail");
        fieldNames.add("status");
        fieldNames.add("updated_at");
        fieldNames.add("created_at");
        fieldNames.add("compaigns_id");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private NotificationColumnInfo columnInfo;
    private ProxyState<com.directoriodelicias.apps.delicias.classes.Notification> proxyState;

    NotificationRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (NotificationColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.directoriodelicias.apps.delicias.classes.Notification>(this);
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
    public String realmGet$label() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.labelIndex);
    }

    @Override
    public void realmSet$label(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.labelIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.labelIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.labelIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.labelIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$label_description() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.label_descriptionIndex);
    }

    @Override
    public void realmSet$label_description(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.label_descriptionIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.label_descriptionIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.label_descriptionIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.label_descriptionIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$auth_type() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.auth_typeIndex);
    }

    @Override
    public void realmSet$auth_type(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.auth_typeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.auth_typeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.auth_typeIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.auth_typeIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$image() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.imageIndex);
    }

    @Override
    public void realmSet$image(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.imageIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.imageIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.imageIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.imageIndex, value);
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
    public int realmGet$auth_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.auth_idIndex);
    }

    @Override
    public void realmSet$auth_id(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.auth_idIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.auth_idIndex, value);
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
    public int realmGet$module_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.module_idIndex);
    }

    @Override
    public void realmSet$module_id(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.module_idIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.module_idIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$detail() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.detailIndex);
    }

    @Override
    public void realmSet$detail(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.detailIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.detailIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.detailIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.detailIndex, value);
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
    public String realmGet$updated_at() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.updated_atIndex);
    }

    @Override
    public void realmSet$updated_at(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.updated_atIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.updated_atIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.updated_atIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.updated_atIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$created_at() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.created_atIndex);
    }

    @Override
    public void realmSet$created_at(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.created_atIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.created_atIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.created_atIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.created_atIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$compaigns_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.compaigns_idIndex);
    }

    @Override
    public void realmSet$compaigns_id(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.compaigns_idIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.compaigns_idIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("Notification", 14, 0);
        builder.addPersistedProperty("id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("label", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("label_description", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("auth_type", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("image", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedLinkProperty("images", RealmFieldType.OBJECT, "Images");
        builder.addPersistedProperty("auth_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("module", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("module_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("detail", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("status", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("updated_at", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("created_at", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("compaigns_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static NotificationColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new NotificationColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "Notification";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.directoriodelicias.apps.delicias.classes.Notification createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(1);
        com.directoriodelicias.apps.delicias.classes.Notification obj = null;
        if (update) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Notification.class);
            NotificationColumnInfo columnInfo = (NotificationColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Notification.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("id")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Notification.class), false, Collections.<String> emptyList());
                    obj = new io.realm.NotificationRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("images")) {
                excludeFields.add("images");
            }
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.NotificationRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Notification.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.NotificationRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Notification.class, json.getInt("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }

        final NotificationRealmProxyInterface objProxy = (NotificationRealmProxyInterface) obj;
        if (json.has("label")) {
            if (json.isNull("label")) {
                objProxy.realmSet$label(null);
            } else {
                objProxy.realmSet$label((String) json.getString("label"));
            }
        }
        if (json.has("label_description")) {
            if (json.isNull("label_description")) {
                objProxy.realmSet$label_description(null);
            } else {
                objProxy.realmSet$label_description((String) json.getString("label_description"));
            }
        }
        if (json.has("auth_type")) {
            if (json.isNull("auth_type")) {
                objProxy.realmSet$auth_type(null);
            } else {
                objProxy.realmSet$auth_type((String) json.getString("auth_type"));
            }
        }
        if (json.has("image")) {
            if (json.isNull("image")) {
                objProxy.realmSet$image(null);
            } else {
                objProxy.realmSet$image((String) json.getString("image"));
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
        if (json.has("auth_id")) {
            if (json.isNull("auth_id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'auth_id' to null.");
            } else {
                objProxy.realmSet$auth_id((int) json.getInt("auth_id"));
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
                throw new IllegalArgumentException("Trying to set non-nullable field 'module_id' to null.");
            } else {
                objProxy.realmSet$module_id((int) json.getInt("module_id"));
            }
        }
        if (json.has("detail")) {
            if (json.isNull("detail")) {
                objProxy.realmSet$detail(null);
            } else {
                objProxy.realmSet$detail((String) json.getString("detail"));
            }
        }
        if (json.has("status")) {
            if (json.isNull("status")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'status' to null.");
            } else {
                objProxy.realmSet$status((int) json.getInt("status"));
            }
        }
        if (json.has("updated_at")) {
            if (json.isNull("updated_at")) {
                objProxy.realmSet$updated_at(null);
            } else {
                objProxy.realmSet$updated_at((String) json.getString("updated_at"));
            }
        }
        if (json.has("created_at")) {
            if (json.isNull("created_at")) {
                objProxy.realmSet$created_at(null);
            } else {
                objProxy.realmSet$created_at((String) json.getString("created_at"));
            }
        }
        if (json.has("compaigns_id")) {
            if (json.isNull("compaigns_id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'compaigns_id' to null.");
            } else {
                objProxy.realmSet$compaigns_id((int) json.getInt("compaigns_id"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.directoriodelicias.apps.delicias.classes.Notification createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.directoriodelicias.apps.delicias.classes.Notification obj = new com.directoriodelicias.apps.delicias.classes.Notification();
        final NotificationRealmProxyInterface objProxy = (NotificationRealmProxyInterface) obj;
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
            } else if (name.equals("label")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$label((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$label(null);
                }
            } else if (name.equals("label_description")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$label_description((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$label_description(null);
                }
            } else if (name.equals("auth_type")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$auth_type((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$auth_type(null);
                }
            } else if (name.equals("image")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$image((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$image(null);
                }
            } else if (name.equals("images")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$images(null);
                } else {
                    com.directoriodelicias.apps.delicias.classes.Images imagesObj = ImagesRealmProxy.createUsingJsonStream(realm, reader);
                    objProxy.realmSet$images(imagesObj);
                }
            } else if (name.equals("auth_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$auth_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'auth_id' to null.");
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
                    objProxy.realmSet$module_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'module_id' to null.");
                }
            } else if (name.equals("detail")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$detail((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$detail(null);
                }
            } else if (name.equals("status")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$status((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'status' to null.");
                }
            } else if (name.equals("updated_at")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$updated_at((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$updated_at(null);
                }
            } else if (name.equals("created_at")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$created_at((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$created_at(null);
                }
            } else if (name.equals("compaigns_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$compaigns_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'compaigns_id' to null.");
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

    public static com.directoriodelicias.apps.delicias.classes.Notification copyOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.Notification object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.directoriodelicias.apps.delicias.classes.Notification) cachedRealmObject;
        }

        com.directoriodelicias.apps.delicias.classes.Notification realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Notification.class);
            NotificationColumnInfo columnInfo = (NotificationColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Notification.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = table.findFirstLong(pkColumnIndex, ((NotificationRealmProxyInterface) object).realmGet$id());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Notification.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.NotificationRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.directoriodelicias.apps.delicias.classes.Notification copy(Realm realm, com.directoriodelicias.apps.delicias.classes.Notification newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.directoriodelicias.apps.delicias.classes.Notification) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.directoriodelicias.apps.delicias.classes.Notification realmObject = realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Notification.class, ((NotificationRealmProxyInterface) newObject).realmGet$id(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        NotificationRealmProxyInterface realmObjectSource = (NotificationRealmProxyInterface) newObject;
        NotificationRealmProxyInterface realmObjectCopy = (NotificationRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$label(realmObjectSource.realmGet$label());
        realmObjectCopy.realmSet$label_description(realmObjectSource.realmGet$label_description());
        realmObjectCopy.realmSet$auth_type(realmObjectSource.realmGet$auth_type());
        realmObjectCopy.realmSet$image(realmObjectSource.realmGet$image());

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
        realmObjectCopy.realmSet$auth_id(realmObjectSource.realmGet$auth_id());
        realmObjectCopy.realmSet$module(realmObjectSource.realmGet$module());
        realmObjectCopy.realmSet$module_id(realmObjectSource.realmGet$module_id());
        realmObjectCopy.realmSet$detail(realmObjectSource.realmGet$detail());
        realmObjectCopy.realmSet$status(realmObjectSource.realmGet$status());
        realmObjectCopy.realmSet$updated_at(realmObjectSource.realmGet$updated_at());
        realmObjectCopy.realmSet$created_at(realmObjectSource.realmGet$created_at());
        realmObjectCopy.realmSet$compaigns_id(realmObjectSource.realmGet$compaigns_id());
        return realmObject;
    }

    public static long insert(Realm realm, com.directoriodelicias.apps.delicias.classes.Notification object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Notification.class);
        long tableNativePtr = table.getNativePtr();
        NotificationColumnInfo columnInfo = (NotificationColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Notification.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((NotificationRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((NotificationRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((NotificationRealmProxyInterface) object).realmGet$id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$label = ((NotificationRealmProxyInterface) object).realmGet$label();
        if (realmGet$label != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.labelIndex, rowIndex, realmGet$label, false);
        }
        String realmGet$label_description = ((NotificationRealmProxyInterface) object).realmGet$label_description();
        if (realmGet$label_description != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.label_descriptionIndex, rowIndex, realmGet$label_description, false);
        }
        String realmGet$auth_type = ((NotificationRealmProxyInterface) object).realmGet$auth_type();
        if (realmGet$auth_type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.auth_typeIndex, rowIndex, realmGet$auth_type, false);
        }
        String realmGet$image = ((NotificationRealmProxyInterface) object).realmGet$image();
        if (realmGet$image != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imageIndex, rowIndex, realmGet$image, false);
        }

        com.directoriodelicias.apps.delicias.classes.Images imagesObj = ((NotificationRealmProxyInterface) object).realmGet$images();
        if (imagesObj != null) {
            Long cacheimages = cache.get(imagesObj);
            if (cacheimages == null) {
                cacheimages = ImagesRealmProxy.insert(realm, imagesObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.imagesIndex, rowIndex, cacheimages, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.auth_idIndex, rowIndex, ((NotificationRealmProxyInterface) object).realmGet$auth_id(), false);
        String realmGet$module = ((NotificationRealmProxyInterface) object).realmGet$module();
        if (realmGet$module != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.moduleIndex, rowIndex, realmGet$module, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.module_idIndex, rowIndex, ((NotificationRealmProxyInterface) object).realmGet$module_id(), false);
        String realmGet$detail = ((NotificationRealmProxyInterface) object).realmGet$detail();
        if (realmGet$detail != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.detailIndex, rowIndex, realmGet$detail, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((NotificationRealmProxyInterface) object).realmGet$status(), false);
        String realmGet$updated_at = ((NotificationRealmProxyInterface) object).realmGet$updated_at();
        if (realmGet$updated_at != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.updated_atIndex, rowIndex, realmGet$updated_at, false);
        }
        String realmGet$created_at = ((NotificationRealmProxyInterface) object).realmGet$created_at();
        if (realmGet$created_at != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.created_atIndex, rowIndex, realmGet$created_at, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.compaigns_idIndex, rowIndex, ((NotificationRealmProxyInterface) object).realmGet$compaigns_id(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Notification.class);
        long tableNativePtr = table.getNativePtr();
        NotificationColumnInfo columnInfo = (NotificationColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Notification.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.directoriodelicias.apps.delicias.classes.Notification object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.Notification) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((NotificationRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((NotificationRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((NotificationRealmProxyInterface) object).realmGet$id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$label = ((NotificationRealmProxyInterface) object).realmGet$label();
            if (realmGet$label != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.labelIndex, rowIndex, realmGet$label, false);
            }
            String realmGet$label_description = ((NotificationRealmProxyInterface) object).realmGet$label_description();
            if (realmGet$label_description != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.label_descriptionIndex, rowIndex, realmGet$label_description, false);
            }
            String realmGet$auth_type = ((NotificationRealmProxyInterface) object).realmGet$auth_type();
            if (realmGet$auth_type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.auth_typeIndex, rowIndex, realmGet$auth_type, false);
            }
            String realmGet$image = ((NotificationRealmProxyInterface) object).realmGet$image();
            if (realmGet$image != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.imageIndex, rowIndex, realmGet$image, false);
            }

            com.directoriodelicias.apps.delicias.classes.Images imagesObj = ((NotificationRealmProxyInterface) object).realmGet$images();
            if (imagesObj != null) {
                Long cacheimages = cache.get(imagesObj);
                if (cacheimages == null) {
                    cacheimages = ImagesRealmProxy.insert(realm, imagesObj, cache);
                }
                table.setLink(columnInfo.imagesIndex, rowIndex, cacheimages, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.auth_idIndex, rowIndex, ((NotificationRealmProxyInterface) object).realmGet$auth_id(), false);
            String realmGet$module = ((NotificationRealmProxyInterface) object).realmGet$module();
            if (realmGet$module != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.moduleIndex, rowIndex, realmGet$module, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.module_idIndex, rowIndex, ((NotificationRealmProxyInterface) object).realmGet$module_id(), false);
            String realmGet$detail = ((NotificationRealmProxyInterface) object).realmGet$detail();
            if (realmGet$detail != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.detailIndex, rowIndex, realmGet$detail, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((NotificationRealmProxyInterface) object).realmGet$status(), false);
            String realmGet$updated_at = ((NotificationRealmProxyInterface) object).realmGet$updated_at();
            if (realmGet$updated_at != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.updated_atIndex, rowIndex, realmGet$updated_at, false);
            }
            String realmGet$created_at = ((NotificationRealmProxyInterface) object).realmGet$created_at();
            if (realmGet$created_at != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.created_atIndex, rowIndex, realmGet$created_at, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.compaigns_idIndex, rowIndex, ((NotificationRealmProxyInterface) object).realmGet$compaigns_id(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.Notification object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Notification.class);
        long tableNativePtr = table.getNativePtr();
        NotificationColumnInfo columnInfo = (NotificationColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Notification.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((NotificationRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((NotificationRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((NotificationRealmProxyInterface) object).realmGet$id());
        }
        cache.put(object, rowIndex);
        String realmGet$label = ((NotificationRealmProxyInterface) object).realmGet$label();
        if (realmGet$label != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.labelIndex, rowIndex, realmGet$label, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.labelIndex, rowIndex, false);
        }
        String realmGet$label_description = ((NotificationRealmProxyInterface) object).realmGet$label_description();
        if (realmGet$label_description != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.label_descriptionIndex, rowIndex, realmGet$label_description, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.label_descriptionIndex, rowIndex, false);
        }
        String realmGet$auth_type = ((NotificationRealmProxyInterface) object).realmGet$auth_type();
        if (realmGet$auth_type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.auth_typeIndex, rowIndex, realmGet$auth_type, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.auth_typeIndex, rowIndex, false);
        }
        String realmGet$image = ((NotificationRealmProxyInterface) object).realmGet$image();
        if (realmGet$image != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imageIndex, rowIndex, realmGet$image, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.imageIndex, rowIndex, false);
        }

        com.directoriodelicias.apps.delicias.classes.Images imagesObj = ((NotificationRealmProxyInterface) object).realmGet$images();
        if (imagesObj != null) {
            Long cacheimages = cache.get(imagesObj);
            if (cacheimages == null) {
                cacheimages = ImagesRealmProxy.insertOrUpdate(realm, imagesObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.imagesIndex, rowIndex, cacheimages, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.imagesIndex, rowIndex);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.auth_idIndex, rowIndex, ((NotificationRealmProxyInterface) object).realmGet$auth_id(), false);
        String realmGet$module = ((NotificationRealmProxyInterface) object).realmGet$module();
        if (realmGet$module != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.moduleIndex, rowIndex, realmGet$module, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.moduleIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.module_idIndex, rowIndex, ((NotificationRealmProxyInterface) object).realmGet$module_id(), false);
        String realmGet$detail = ((NotificationRealmProxyInterface) object).realmGet$detail();
        if (realmGet$detail != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.detailIndex, rowIndex, realmGet$detail, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.detailIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((NotificationRealmProxyInterface) object).realmGet$status(), false);
        String realmGet$updated_at = ((NotificationRealmProxyInterface) object).realmGet$updated_at();
        if (realmGet$updated_at != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.updated_atIndex, rowIndex, realmGet$updated_at, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.updated_atIndex, rowIndex, false);
        }
        String realmGet$created_at = ((NotificationRealmProxyInterface) object).realmGet$created_at();
        if (realmGet$created_at != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.created_atIndex, rowIndex, realmGet$created_at, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.created_atIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.compaigns_idIndex, rowIndex, ((NotificationRealmProxyInterface) object).realmGet$compaigns_id(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Notification.class);
        long tableNativePtr = table.getNativePtr();
        NotificationColumnInfo columnInfo = (NotificationColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Notification.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.directoriodelicias.apps.delicias.classes.Notification object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.Notification) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((NotificationRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((NotificationRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((NotificationRealmProxyInterface) object).realmGet$id());
            }
            cache.put(object, rowIndex);
            String realmGet$label = ((NotificationRealmProxyInterface) object).realmGet$label();
            if (realmGet$label != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.labelIndex, rowIndex, realmGet$label, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.labelIndex, rowIndex, false);
            }
            String realmGet$label_description = ((NotificationRealmProxyInterface) object).realmGet$label_description();
            if (realmGet$label_description != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.label_descriptionIndex, rowIndex, realmGet$label_description, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.label_descriptionIndex, rowIndex, false);
            }
            String realmGet$auth_type = ((NotificationRealmProxyInterface) object).realmGet$auth_type();
            if (realmGet$auth_type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.auth_typeIndex, rowIndex, realmGet$auth_type, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.auth_typeIndex, rowIndex, false);
            }
            String realmGet$image = ((NotificationRealmProxyInterface) object).realmGet$image();
            if (realmGet$image != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.imageIndex, rowIndex, realmGet$image, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.imageIndex, rowIndex, false);
            }

            com.directoriodelicias.apps.delicias.classes.Images imagesObj = ((NotificationRealmProxyInterface) object).realmGet$images();
            if (imagesObj != null) {
                Long cacheimages = cache.get(imagesObj);
                if (cacheimages == null) {
                    cacheimages = ImagesRealmProxy.insertOrUpdate(realm, imagesObj, cache);
                }
                Table.nativeSetLink(tableNativePtr, columnInfo.imagesIndex, rowIndex, cacheimages, false);
            } else {
                Table.nativeNullifyLink(tableNativePtr, columnInfo.imagesIndex, rowIndex);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.auth_idIndex, rowIndex, ((NotificationRealmProxyInterface) object).realmGet$auth_id(), false);
            String realmGet$module = ((NotificationRealmProxyInterface) object).realmGet$module();
            if (realmGet$module != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.moduleIndex, rowIndex, realmGet$module, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.moduleIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.module_idIndex, rowIndex, ((NotificationRealmProxyInterface) object).realmGet$module_id(), false);
            String realmGet$detail = ((NotificationRealmProxyInterface) object).realmGet$detail();
            if (realmGet$detail != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.detailIndex, rowIndex, realmGet$detail, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.detailIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((NotificationRealmProxyInterface) object).realmGet$status(), false);
            String realmGet$updated_at = ((NotificationRealmProxyInterface) object).realmGet$updated_at();
            if (realmGet$updated_at != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.updated_atIndex, rowIndex, realmGet$updated_at, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.updated_atIndex, rowIndex, false);
            }
            String realmGet$created_at = ((NotificationRealmProxyInterface) object).realmGet$created_at();
            if (realmGet$created_at != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.created_atIndex, rowIndex, realmGet$created_at, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.created_atIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.compaigns_idIndex, rowIndex, ((NotificationRealmProxyInterface) object).realmGet$compaigns_id(), false);
        }
    }

    public static com.directoriodelicias.apps.delicias.classes.Notification createDetachedCopy(com.directoriodelicias.apps.delicias.classes.Notification realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.directoriodelicias.apps.delicias.classes.Notification unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.directoriodelicias.apps.delicias.classes.Notification();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.directoriodelicias.apps.delicias.classes.Notification) cachedObject.object;
            }
            unmanagedObject = (com.directoriodelicias.apps.delicias.classes.Notification) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        NotificationRealmProxyInterface unmanagedCopy = (NotificationRealmProxyInterface) unmanagedObject;
        NotificationRealmProxyInterface realmSource = (NotificationRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());
        unmanagedCopy.realmSet$label(realmSource.realmGet$label());
        unmanagedCopy.realmSet$label_description(realmSource.realmGet$label_description());
        unmanagedCopy.realmSet$auth_type(realmSource.realmGet$auth_type());
        unmanagedCopy.realmSet$image(realmSource.realmGet$image());

        // Deep copy of images
        unmanagedCopy.realmSet$images(ImagesRealmProxy.createDetachedCopy(realmSource.realmGet$images(), currentDepth + 1, maxDepth, cache));
        unmanagedCopy.realmSet$auth_id(realmSource.realmGet$auth_id());
        unmanagedCopy.realmSet$module(realmSource.realmGet$module());
        unmanagedCopy.realmSet$module_id(realmSource.realmGet$module_id());
        unmanagedCopy.realmSet$detail(realmSource.realmGet$detail());
        unmanagedCopy.realmSet$status(realmSource.realmGet$status());
        unmanagedCopy.realmSet$updated_at(realmSource.realmGet$updated_at());
        unmanagedCopy.realmSet$created_at(realmSource.realmGet$created_at());
        unmanagedCopy.realmSet$compaigns_id(realmSource.realmGet$compaigns_id());

        return unmanagedObject;
    }

    static com.directoriodelicias.apps.delicias.classes.Notification update(Realm realm, com.directoriodelicias.apps.delicias.classes.Notification realmObject, com.directoriodelicias.apps.delicias.classes.Notification newObject, Map<RealmModel, RealmObjectProxy> cache) {
        NotificationRealmProxyInterface realmObjectTarget = (NotificationRealmProxyInterface) realmObject;
        NotificationRealmProxyInterface realmObjectSource = (NotificationRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$label(realmObjectSource.realmGet$label());
        realmObjectTarget.realmSet$label_description(realmObjectSource.realmGet$label_description());
        realmObjectTarget.realmSet$auth_type(realmObjectSource.realmGet$auth_type());
        realmObjectTarget.realmSet$image(realmObjectSource.realmGet$image());
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
        realmObjectTarget.realmSet$auth_id(realmObjectSource.realmGet$auth_id());
        realmObjectTarget.realmSet$module(realmObjectSource.realmGet$module());
        realmObjectTarget.realmSet$module_id(realmObjectSource.realmGet$module_id());
        realmObjectTarget.realmSet$detail(realmObjectSource.realmGet$detail());
        realmObjectTarget.realmSet$status(realmObjectSource.realmGet$status());
        realmObjectTarget.realmSet$updated_at(realmObjectSource.realmGet$updated_at());
        realmObjectTarget.realmSet$created_at(realmObjectSource.realmGet$created_at());
        realmObjectTarget.realmSet$compaigns_id(realmObjectSource.realmGet$compaigns_id());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Notification = proxy[");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{label:");
        stringBuilder.append(realmGet$label() != null ? realmGet$label() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{label_description:");
        stringBuilder.append(realmGet$label_description() != null ? realmGet$label_description() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{auth_type:");
        stringBuilder.append(realmGet$auth_type() != null ? realmGet$auth_type() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{image:");
        stringBuilder.append(realmGet$image() != null ? realmGet$image() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{images:");
        stringBuilder.append(realmGet$images() != null ? "Images" : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{auth_id:");
        stringBuilder.append(realmGet$auth_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{module:");
        stringBuilder.append(realmGet$module() != null ? realmGet$module() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{module_id:");
        stringBuilder.append(realmGet$module_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{detail:");
        stringBuilder.append(realmGet$detail() != null ? realmGet$detail() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{status:");
        stringBuilder.append(realmGet$status());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{updated_at:");
        stringBuilder.append(realmGet$updated_at() != null ? realmGet$updated_at() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{created_at:");
        stringBuilder.append(realmGet$created_at() != null ? realmGet$created_at() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{compaigns_id:");
        stringBuilder.append(realmGet$compaigns_id());
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
        NotificationRealmProxy aNotification = (NotificationRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aNotification.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aNotification.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aNotification.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
