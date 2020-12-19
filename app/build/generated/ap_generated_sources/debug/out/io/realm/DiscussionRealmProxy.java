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
public class DiscussionRealmProxy extends com.directoriodelicias.apps.delicias.classes.Discussion
    implements RealmObjectProxy, DiscussionRealmProxyInterface {

    static final class DiscussionColumnInfo extends ColumnInfo {
        long discussionIdIndex;
        long senderUserIndex;
        long receiverIdIndex;
        long messagesIndex;
        long createdAtIndex;
        long statusIndex;
        long isSystemIndex;

        DiscussionColumnInfo(OsSchemaInfo schemaInfo) {
            super(7);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("Discussion");
            this.discussionIdIndex = addColumnDetails("discussionId", objectSchemaInfo);
            this.senderUserIndex = addColumnDetails("senderUser", objectSchemaInfo);
            this.receiverIdIndex = addColumnDetails("receiverId", objectSchemaInfo);
            this.messagesIndex = addColumnDetails("messages", objectSchemaInfo);
            this.createdAtIndex = addColumnDetails("createdAt", objectSchemaInfo);
            this.statusIndex = addColumnDetails("status", objectSchemaInfo);
            this.isSystemIndex = addColumnDetails("isSystem", objectSchemaInfo);
        }

        DiscussionColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new DiscussionColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final DiscussionColumnInfo src = (DiscussionColumnInfo) rawSrc;
            final DiscussionColumnInfo dst = (DiscussionColumnInfo) rawDst;
            dst.discussionIdIndex = src.discussionIdIndex;
            dst.senderUserIndex = src.senderUserIndex;
            dst.receiverIdIndex = src.receiverIdIndex;
            dst.messagesIndex = src.messagesIndex;
            dst.createdAtIndex = src.createdAtIndex;
            dst.statusIndex = src.statusIndex;
            dst.isSystemIndex = src.isSystemIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>(7);
        fieldNames.add("discussionId");
        fieldNames.add("senderUser");
        fieldNames.add("receiverId");
        fieldNames.add("messages");
        fieldNames.add("createdAt");
        fieldNames.add("status");
        fieldNames.add("isSystem");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private DiscussionColumnInfo columnInfo;
    private ProxyState<com.directoriodelicias.apps.delicias.classes.Discussion> proxyState;
    private RealmList<com.directoriodelicias.apps.delicias.classes.Message> messagesRealmList;

    DiscussionRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (DiscussionColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.directoriodelicias.apps.delicias.classes.Discussion>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$discussionId() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.discussionIdIndex);
    }

    @Override
    public void realmSet$discussionId(int value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'discussionId' cannot be changed after object was created.");
    }

    @Override
    public com.directoriodelicias.apps.delicias.classes.User realmGet$senderUser() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNullLink(columnInfo.senderUserIndex)) {
            return null;
        }
        return proxyState.getRealm$realm().get(com.directoriodelicias.apps.delicias.classes.User.class, proxyState.getRow$realm().getLink(columnInfo.senderUserIndex), false, Collections.<String>emptyList());
    }

    @Override
    public void realmSet$senderUser(com.directoriodelicias.apps.delicias.classes.User value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("senderUser")) {
                return;
            }
            if (value != null && !RealmObject.isManaged(value)) {
                value = ((Realm) proxyState.getRealm$realm()).copyToRealm(value);
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                // Table#nullifyLink() does not support default value. Just using Row.
                row.nullifyLink(columnInfo.senderUserIndex);
                return;
            }
            proxyState.checkValidObject(value);
            row.getTable().setLink(columnInfo.senderUserIndex, row.getIndex(), ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().nullifyLink(columnInfo.senderUserIndex);
            return;
        }
        proxyState.checkValidObject(value);
        proxyState.getRow$realm().setLink(columnInfo.senderUserIndex, ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex());
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$receiverId() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.receiverIdIndex);
    }

    @Override
    public void realmSet$receiverId(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.receiverIdIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.receiverIdIndex, value);
    }

    @Override
    public RealmList<com.directoriodelicias.apps.delicias.classes.Message> realmGet$messages() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (messagesRealmList != null) {
            return messagesRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.messagesIndex);
            messagesRealmList = new RealmList<com.directoriodelicias.apps.delicias.classes.Message>(com.directoriodelicias.apps.delicias.classes.Message.class, osList, proxyState.getRealm$realm());
            return messagesRealmList;
        }
    }

    @Override
    public void realmSet$messages(RealmList<com.directoriodelicias.apps.delicias.classes.Message> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("messages")) {
                return;
            }
            // if the list contains unmanaged RealmObjects, convert them to managed.
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.directoriodelicias.apps.delicias.classes.Message> original = value;
                value = new RealmList<com.directoriodelicias.apps.delicias.classes.Message>();
                for (com.directoriodelicias.apps.delicias.classes.Message item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.messagesIndex);
        // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
        if (value != null && value.size() == osList.size()) {
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.directoriodelicias.apps.delicias.classes.Message linkedObject = value.get(i);
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
                com.directoriodelicias.apps.delicias.classes.Message linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.addRow(((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        }
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$createdAt() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.createdAtIndex);
    }

    @Override
    public void realmSet$createdAt(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.createdAtIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.createdAtIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.createdAtIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.createdAtIndex, value);
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
    public boolean realmGet$isSystem() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.isSystemIndex);
    }

    @Override
    public void realmSet$isSystem(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.isSystemIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.isSystemIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("Discussion", 7, 0);
        builder.addPersistedProperty("discussionId", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedLinkProperty("senderUser", RealmFieldType.OBJECT, "User");
        builder.addPersistedProperty("receiverId", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedLinkProperty("messages", RealmFieldType.LIST, "Message");
        builder.addPersistedProperty("createdAt", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("status", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("isSystem", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static DiscussionColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new DiscussionColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "Discussion";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.directoriodelicias.apps.delicias.classes.Discussion createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(2);
        com.directoriodelicias.apps.delicias.classes.Discussion obj = null;
        if (update) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Discussion.class);
            DiscussionColumnInfo columnInfo = (DiscussionColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Discussion.class);
            long pkColumnIndex = columnInfo.discussionIdIndex;
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("discussionId")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("discussionId"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Discussion.class), false, Collections.<String> emptyList());
                    obj = new io.realm.DiscussionRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("senderUser")) {
                excludeFields.add("senderUser");
            }
            if (json.has("messages")) {
                excludeFields.add("messages");
            }
            if (json.has("discussionId")) {
                if (json.isNull("discussionId")) {
                    obj = (io.realm.DiscussionRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Discussion.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.DiscussionRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Discussion.class, json.getInt("discussionId"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'discussionId'.");
            }
        }

        final DiscussionRealmProxyInterface objProxy = (DiscussionRealmProxyInterface) obj;
        if (json.has("senderUser")) {
            if (json.isNull("senderUser")) {
                objProxy.realmSet$senderUser(null);
            } else {
                com.directoriodelicias.apps.delicias.classes.User senderUserObj = UserRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("senderUser"), update);
                objProxy.realmSet$senderUser(senderUserObj);
            }
        }
        if (json.has("receiverId")) {
            if (json.isNull("receiverId")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'receiverId' to null.");
            } else {
                objProxy.realmSet$receiverId((int) json.getInt("receiverId"));
            }
        }
        if (json.has("messages")) {
            if (json.isNull("messages")) {
                objProxy.realmSet$messages(null);
            } else {
                objProxy.realmGet$messages().clear();
                JSONArray array = json.getJSONArray("messages");
                for (int i = 0; i < array.length(); i++) {
                    com.directoriodelicias.apps.delicias.classes.Message item = MessageRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$messages().add(item);
                }
            }
        }
        if (json.has("createdAt")) {
            if (json.isNull("createdAt")) {
                objProxy.realmSet$createdAt(null);
            } else {
                objProxy.realmSet$createdAt((String) json.getString("createdAt"));
            }
        }
        if (json.has("status")) {
            if (json.isNull("status")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'status' to null.");
            } else {
                objProxy.realmSet$status((int) json.getInt("status"));
            }
        }
        if (json.has("isSystem")) {
            if (json.isNull("isSystem")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'isSystem' to null.");
            } else {
                objProxy.realmSet$isSystem((boolean) json.getBoolean("isSystem"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.directoriodelicias.apps.delicias.classes.Discussion createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.directoriodelicias.apps.delicias.classes.Discussion obj = new com.directoriodelicias.apps.delicias.classes.Discussion();
        final DiscussionRealmProxyInterface objProxy = (DiscussionRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("discussionId")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$discussionId((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'discussionId' to null.");
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("senderUser")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$senderUser(null);
                } else {
                    com.directoriodelicias.apps.delicias.classes.User senderUserObj = UserRealmProxy.createUsingJsonStream(realm, reader);
                    objProxy.realmSet$senderUser(senderUserObj);
                }
            } else if (name.equals("receiverId")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$receiverId((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'receiverId' to null.");
                }
            } else if (name.equals("messages")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$messages(null);
                } else {
                    objProxy.realmSet$messages(new RealmList<com.directoriodelicias.apps.delicias.classes.Message>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.directoriodelicias.apps.delicias.classes.Message item = MessageRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$messages().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("createdAt")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$createdAt((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$createdAt(null);
                }
            } else if (name.equals("status")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$status((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'status' to null.");
                }
            } else if (name.equals("isSystem")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$isSystem((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isSystem' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'discussionId'.");
        }
        return realm.copyToRealm(obj);
    }

    public static com.directoriodelicias.apps.delicias.classes.Discussion copyOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.Discussion object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.directoriodelicias.apps.delicias.classes.Discussion) cachedRealmObject;
        }

        com.directoriodelicias.apps.delicias.classes.Discussion realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Discussion.class);
            DiscussionColumnInfo columnInfo = (DiscussionColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Discussion.class);
            long pkColumnIndex = columnInfo.discussionIdIndex;
            long rowIndex = table.findFirstLong(pkColumnIndex, ((DiscussionRealmProxyInterface) object).realmGet$discussionId());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Discussion.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.DiscussionRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.directoriodelicias.apps.delicias.classes.Discussion copy(Realm realm, com.directoriodelicias.apps.delicias.classes.Discussion newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.directoriodelicias.apps.delicias.classes.Discussion) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.directoriodelicias.apps.delicias.classes.Discussion realmObject = realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Discussion.class, ((DiscussionRealmProxyInterface) newObject).realmGet$discussionId(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        DiscussionRealmProxyInterface realmObjectSource = (DiscussionRealmProxyInterface) newObject;
        DiscussionRealmProxyInterface realmObjectCopy = (DiscussionRealmProxyInterface) realmObject;


        com.directoriodelicias.apps.delicias.classes.User senderUserObj = realmObjectSource.realmGet$senderUser();
        if (senderUserObj == null) {
            realmObjectCopy.realmSet$senderUser(null);
        } else {
            com.directoriodelicias.apps.delicias.classes.User cachesenderUser = (com.directoriodelicias.apps.delicias.classes.User) cache.get(senderUserObj);
            if (cachesenderUser != null) {
                realmObjectCopy.realmSet$senderUser(cachesenderUser);
            } else {
                realmObjectCopy.realmSet$senderUser(UserRealmProxy.copyOrUpdate(realm, senderUserObj, update, cache));
            }
        }
        realmObjectCopy.realmSet$receiverId(realmObjectSource.realmGet$receiverId());

        RealmList<com.directoriodelicias.apps.delicias.classes.Message> messagesList = realmObjectSource.realmGet$messages();
        if (messagesList != null) {
            RealmList<com.directoriodelicias.apps.delicias.classes.Message> messagesRealmList = realmObjectCopy.realmGet$messages();
            messagesRealmList.clear();
            for (int i = 0; i < messagesList.size(); i++) {
                com.directoriodelicias.apps.delicias.classes.Message messagesItem = messagesList.get(i);
                com.directoriodelicias.apps.delicias.classes.Message cachemessages = (com.directoriodelicias.apps.delicias.classes.Message) cache.get(messagesItem);
                if (cachemessages != null) {
                    messagesRealmList.add(cachemessages);
                } else {
                    messagesRealmList.add(MessageRealmProxy.copyOrUpdate(realm, messagesItem, update, cache));
                }
            }
        }

        realmObjectCopy.realmSet$createdAt(realmObjectSource.realmGet$createdAt());
        realmObjectCopy.realmSet$status(realmObjectSource.realmGet$status());
        realmObjectCopy.realmSet$isSystem(realmObjectSource.realmGet$isSystem());
        return realmObject;
    }

    public static long insert(Realm realm, com.directoriodelicias.apps.delicias.classes.Discussion object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Discussion.class);
        long tableNativePtr = table.getNativePtr();
        DiscussionColumnInfo columnInfo = (DiscussionColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Discussion.class);
        long pkColumnIndex = columnInfo.discussionIdIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((DiscussionRealmProxyInterface) object).realmGet$discussionId();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((DiscussionRealmProxyInterface) object).realmGet$discussionId());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((DiscussionRealmProxyInterface) object).realmGet$discussionId());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);

        com.directoriodelicias.apps.delicias.classes.User senderUserObj = ((DiscussionRealmProxyInterface) object).realmGet$senderUser();
        if (senderUserObj != null) {
            Long cachesenderUser = cache.get(senderUserObj);
            if (cachesenderUser == null) {
                cachesenderUser = UserRealmProxy.insert(realm, senderUserObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.senderUserIndex, rowIndex, cachesenderUser, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.receiverIdIndex, rowIndex, ((DiscussionRealmProxyInterface) object).realmGet$receiverId(), false);

        RealmList<com.directoriodelicias.apps.delicias.classes.Message> messagesList = ((DiscussionRealmProxyInterface) object).realmGet$messages();
        if (messagesList != null) {
            OsList messagesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.messagesIndex);
            for (com.directoriodelicias.apps.delicias.classes.Message messagesItem : messagesList) {
                Long cacheItemIndexmessages = cache.get(messagesItem);
                if (cacheItemIndexmessages == null) {
                    cacheItemIndexmessages = MessageRealmProxy.insert(realm, messagesItem, cache);
                }
                messagesOsList.addRow(cacheItemIndexmessages);
            }
        }
        String realmGet$createdAt = ((DiscussionRealmProxyInterface) object).realmGet$createdAt();
        if (realmGet$createdAt != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.createdAtIndex, rowIndex, realmGet$createdAt, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((DiscussionRealmProxyInterface) object).realmGet$status(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.isSystemIndex, rowIndex, ((DiscussionRealmProxyInterface) object).realmGet$isSystem(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Discussion.class);
        long tableNativePtr = table.getNativePtr();
        DiscussionColumnInfo columnInfo = (DiscussionColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Discussion.class);
        long pkColumnIndex = columnInfo.discussionIdIndex;
        com.directoriodelicias.apps.delicias.classes.Discussion object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.Discussion) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((DiscussionRealmProxyInterface) object).realmGet$discussionId();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((DiscussionRealmProxyInterface) object).realmGet$discussionId());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((DiscussionRealmProxyInterface) object).realmGet$discussionId());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);

            com.directoriodelicias.apps.delicias.classes.User senderUserObj = ((DiscussionRealmProxyInterface) object).realmGet$senderUser();
            if (senderUserObj != null) {
                Long cachesenderUser = cache.get(senderUserObj);
                if (cachesenderUser == null) {
                    cachesenderUser = UserRealmProxy.insert(realm, senderUserObj, cache);
                }
                table.setLink(columnInfo.senderUserIndex, rowIndex, cachesenderUser, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.receiverIdIndex, rowIndex, ((DiscussionRealmProxyInterface) object).realmGet$receiverId(), false);

            RealmList<com.directoriodelicias.apps.delicias.classes.Message> messagesList = ((DiscussionRealmProxyInterface) object).realmGet$messages();
            if (messagesList != null) {
                OsList messagesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.messagesIndex);
                for (com.directoriodelicias.apps.delicias.classes.Message messagesItem : messagesList) {
                    Long cacheItemIndexmessages = cache.get(messagesItem);
                    if (cacheItemIndexmessages == null) {
                        cacheItemIndexmessages = MessageRealmProxy.insert(realm, messagesItem, cache);
                    }
                    messagesOsList.addRow(cacheItemIndexmessages);
                }
            }
            String realmGet$createdAt = ((DiscussionRealmProxyInterface) object).realmGet$createdAt();
            if (realmGet$createdAt != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.createdAtIndex, rowIndex, realmGet$createdAt, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((DiscussionRealmProxyInterface) object).realmGet$status(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.isSystemIndex, rowIndex, ((DiscussionRealmProxyInterface) object).realmGet$isSystem(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.Discussion object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Discussion.class);
        long tableNativePtr = table.getNativePtr();
        DiscussionColumnInfo columnInfo = (DiscussionColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Discussion.class);
        long pkColumnIndex = columnInfo.discussionIdIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((DiscussionRealmProxyInterface) object).realmGet$discussionId();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((DiscussionRealmProxyInterface) object).realmGet$discussionId());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((DiscussionRealmProxyInterface) object).realmGet$discussionId());
        }
        cache.put(object, rowIndex);

        com.directoriodelicias.apps.delicias.classes.User senderUserObj = ((DiscussionRealmProxyInterface) object).realmGet$senderUser();
        if (senderUserObj != null) {
            Long cachesenderUser = cache.get(senderUserObj);
            if (cachesenderUser == null) {
                cachesenderUser = UserRealmProxy.insertOrUpdate(realm, senderUserObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.senderUserIndex, rowIndex, cachesenderUser, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.senderUserIndex, rowIndex);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.receiverIdIndex, rowIndex, ((DiscussionRealmProxyInterface) object).realmGet$receiverId(), false);

        OsList messagesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.messagesIndex);
        RealmList<com.directoriodelicias.apps.delicias.classes.Message> messagesList = ((DiscussionRealmProxyInterface) object).realmGet$messages();
        if (messagesList != null && messagesList.size() == messagesOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = messagesList.size();
            for (int i = 0; i < objects; i++) {
                com.directoriodelicias.apps.delicias.classes.Message messagesItem = messagesList.get(i);
                Long cacheItemIndexmessages = cache.get(messagesItem);
                if (cacheItemIndexmessages == null) {
                    cacheItemIndexmessages = MessageRealmProxy.insertOrUpdate(realm, messagesItem, cache);
                }
                messagesOsList.setRow(i, cacheItemIndexmessages);
            }
        } else {
            messagesOsList.removeAll();
            if (messagesList != null) {
                for (com.directoriodelicias.apps.delicias.classes.Message messagesItem : messagesList) {
                    Long cacheItemIndexmessages = cache.get(messagesItem);
                    if (cacheItemIndexmessages == null) {
                        cacheItemIndexmessages = MessageRealmProxy.insertOrUpdate(realm, messagesItem, cache);
                    }
                    messagesOsList.addRow(cacheItemIndexmessages);
                }
            }
        }

        String realmGet$createdAt = ((DiscussionRealmProxyInterface) object).realmGet$createdAt();
        if (realmGet$createdAt != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.createdAtIndex, rowIndex, realmGet$createdAt, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.createdAtIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((DiscussionRealmProxyInterface) object).realmGet$status(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.isSystemIndex, rowIndex, ((DiscussionRealmProxyInterface) object).realmGet$isSystem(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Discussion.class);
        long tableNativePtr = table.getNativePtr();
        DiscussionColumnInfo columnInfo = (DiscussionColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Discussion.class);
        long pkColumnIndex = columnInfo.discussionIdIndex;
        com.directoriodelicias.apps.delicias.classes.Discussion object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.Discussion) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((DiscussionRealmProxyInterface) object).realmGet$discussionId();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((DiscussionRealmProxyInterface) object).realmGet$discussionId());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((DiscussionRealmProxyInterface) object).realmGet$discussionId());
            }
            cache.put(object, rowIndex);

            com.directoriodelicias.apps.delicias.classes.User senderUserObj = ((DiscussionRealmProxyInterface) object).realmGet$senderUser();
            if (senderUserObj != null) {
                Long cachesenderUser = cache.get(senderUserObj);
                if (cachesenderUser == null) {
                    cachesenderUser = UserRealmProxy.insertOrUpdate(realm, senderUserObj, cache);
                }
                Table.nativeSetLink(tableNativePtr, columnInfo.senderUserIndex, rowIndex, cachesenderUser, false);
            } else {
                Table.nativeNullifyLink(tableNativePtr, columnInfo.senderUserIndex, rowIndex);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.receiverIdIndex, rowIndex, ((DiscussionRealmProxyInterface) object).realmGet$receiverId(), false);

            OsList messagesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.messagesIndex);
            RealmList<com.directoriodelicias.apps.delicias.classes.Message> messagesList = ((DiscussionRealmProxyInterface) object).realmGet$messages();
            if (messagesList != null && messagesList.size() == messagesOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = messagesList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.directoriodelicias.apps.delicias.classes.Message messagesItem = messagesList.get(i);
                    Long cacheItemIndexmessages = cache.get(messagesItem);
                    if (cacheItemIndexmessages == null) {
                        cacheItemIndexmessages = MessageRealmProxy.insertOrUpdate(realm, messagesItem, cache);
                    }
                    messagesOsList.setRow(i, cacheItemIndexmessages);
                }
            } else {
                messagesOsList.removeAll();
                if (messagesList != null) {
                    for (com.directoriodelicias.apps.delicias.classes.Message messagesItem : messagesList) {
                        Long cacheItemIndexmessages = cache.get(messagesItem);
                        if (cacheItemIndexmessages == null) {
                            cacheItemIndexmessages = MessageRealmProxy.insertOrUpdate(realm, messagesItem, cache);
                        }
                        messagesOsList.addRow(cacheItemIndexmessages);
                    }
                }
            }

            String realmGet$createdAt = ((DiscussionRealmProxyInterface) object).realmGet$createdAt();
            if (realmGet$createdAt != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.createdAtIndex, rowIndex, realmGet$createdAt, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.createdAtIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((DiscussionRealmProxyInterface) object).realmGet$status(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.isSystemIndex, rowIndex, ((DiscussionRealmProxyInterface) object).realmGet$isSystem(), false);
        }
    }

    public static com.directoriodelicias.apps.delicias.classes.Discussion createDetachedCopy(com.directoriodelicias.apps.delicias.classes.Discussion realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.directoriodelicias.apps.delicias.classes.Discussion unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.directoriodelicias.apps.delicias.classes.Discussion();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.directoriodelicias.apps.delicias.classes.Discussion) cachedObject.object;
            }
            unmanagedObject = (com.directoriodelicias.apps.delicias.classes.Discussion) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        DiscussionRealmProxyInterface unmanagedCopy = (DiscussionRealmProxyInterface) unmanagedObject;
        DiscussionRealmProxyInterface realmSource = (DiscussionRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$discussionId(realmSource.realmGet$discussionId());

        // Deep copy of senderUser
        unmanagedCopy.realmSet$senderUser(UserRealmProxy.createDetachedCopy(realmSource.realmGet$senderUser(), currentDepth + 1, maxDepth, cache));
        unmanagedCopy.realmSet$receiverId(realmSource.realmGet$receiverId());

        // Deep copy of messages
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$messages(null);
        } else {
            RealmList<com.directoriodelicias.apps.delicias.classes.Message> managedmessagesList = realmSource.realmGet$messages();
            RealmList<com.directoriodelicias.apps.delicias.classes.Message> unmanagedmessagesList = new RealmList<com.directoriodelicias.apps.delicias.classes.Message>();
            unmanagedCopy.realmSet$messages(unmanagedmessagesList);
            int nextDepth = currentDepth + 1;
            int size = managedmessagesList.size();
            for (int i = 0; i < size; i++) {
                com.directoriodelicias.apps.delicias.classes.Message item = MessageRealmProxy.createDetachedCopy(managedmessagesList.get(i), nextDepth, maxDepth, cache);
                unmanagedmessagesList.add(item);
            }
        }
        unmanagedCopy.realmSet$createdAt(realmSource.realmGet$createdAt());
        unmanagedCopy.realmSet$status(realmSource.realmGet$status());
        unmanagedCopy.realmSet$isSystem(realmSource.realmGet$isSystem());

        return unmanagedObject;
    }

    static com.directoriodelicias.apps.delicias.classes.Discussion update(Realm realm, com.directoriodelicias.apps.delicias.classes.Discussion realmObject, com.directoriodelicias.apps.delicias.classes.Discussion newObject, Map<RealmModel, RealmObjectProxy> cache) {
        DiscussionRealmProxyInterface realmObjectTarget = (DiscussionRealmProxyInterface) realmObject;
        DiscussionRealmProxyInterface realmObjectSource = (DiscussionRealmProxyInterface) newObject;
        com.directoriodelicias.apps.delicias.classes.User senderUserObj = realmObjectSource.realmGet$senderUser();
        if (senderUserObj == null) {
            realmObjectTarget.realmSet$senderUser(null);
        } else {
            com.directoriodelicias.apps.delicias.classes.User cachesenderUser = (com.directoriodelicias.apps.delicias.classes.User) cache.get(senderUserObj);
            if (cachesenderUser != null) {
                realmObjectTarget.realmSet$senderUser(cachesenderUser);
            } else {
                realmObjectTarget.realmSet$senderUser(UserRealmProxy.copyOrUpdate(realm, senderUserObj, true, cache));
            }
        }
        realmObjectTarget.realmSet$receiverId(realmObjectSource.realmGet$receiverId());
        RealmList<com.directoriodelicias.apps.delicias.classes.Message> messagesList = realmObjectSource.realmGet$messages();
        RealmList<com.directoriodelicias.apps.delicias.classes.Message> messagesRealmList = realmObjectTarget.realmGet$messages();
        if (messagesList != null && messagesList.size() == messagesRealmList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = messagesList.size();
            for (int i = 0; i < objects; i++) {
                com.directoriodelicias.apps.delicias.classes.Message messagesItem = messagesList.get(i);
                com.directoriodelicias.apps.delicias.classes.Message cachemessages = (com.directoriodelicias.apps.delicias.classes.Message) cache.get(messagesItem);
                if (cachemessages != null) {
                    messagesRealmList.set(i, cachemessages);
                } else {
                    messagesRealmList.set(i, MessageRealmProxy.copyOrUpdate(realm, messagesItem, true, cache));
                }
            }
        } else {
            messagesRealmList.clear();
            if (messagesList != null) {
                for (int i = 0; i < messagesList.size(); i++) {
                    com.directoriodelicias.apps.delicias.classes.Message messagesItem = messagesList.get(i);
                    com.directoriodelicias.apps.delicias.classes.Message cachemessages = (com.directoriodelicias.apps.delicias.classes.Message) cache.get(messagesItem);
                    if (cachemessages != null) {
                        messagesRealmList.add(cachemessages);
                    } else {
                        messagesRealmList.add(MessageRealmProxy.copyOrUpdate(realm, messagesItem, true, cache));
                    }
                }
            }
        }
        realmObjectTarget.realmSet$createdAt(realmObjectSource.realmGet$createdAt());
        realmObjectTarget.realmSet$status(realmObjectSource.realmGet$status());
        realmObjectTarget.realmSet$isSystem(realmObjectSource.realmGet$isSystem());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Discussion = proxy[");
        stringBuilder.append("{discussionId:");
        stringBuilder.append(realmGet$discussionId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{senderUser:");
        stringBuilder.append(realmGet$senderUser() != null ? "User" : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{receiverId:");
        stringBuilder.append(realmGet$receiverId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{messages:");
        stringBuilder.append("RealmList<Message>[").append(realmGet$messages().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{createdAt:");
        stringBuilder.append(realmGet$createdAt() != null ? realmGet$createdAt() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{status:");
        stringBuilder.append(realmGet$status());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{isSystem:");
        stringBuilder.append(realmGet$isSystem());
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
        DiscussionRealmProxy aDiscussion = (DiscussionRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aDiscussion.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aDiscussion.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aDiscussion.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
