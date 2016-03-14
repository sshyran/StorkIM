package org.tigase.messenger.phone.pro.db;

import android.provider.BaseColumns;

import tigase.jaxmpp.android.roster.RosterItemsCacheTableMetaData;

public final class DatabaseContract {

    public static final int DATABASE_VERSION = 5;

    public static final String DATABASE_NAME = "mobile_messenger1.db";

    private DatabaseContract() {
    }


    public static abstract class RosterItemsGroupsCache implements BaseColumns {
        public static final String FIELD_ITEM = "item_id";
        public static final String FIELD_GROUP = "group_id";
        public static final String TABLE_NAME = "roster_items_groups";
        private static final String CREATE_TABLE =
                "CREATE TABLE "
                        + RosterItemsGroupsCache.TABLE_NAME + " ("
                        + RosterItemsGroupsCache.FIELD_ITEM + " INTEGER, "
                        + RosterItemsGroupsCache.FIELD_GROUP + " INTEGER, "
                        + "FOREIGN KEY(" + RosterItemsGroupsCache.FIELD_ITEM + ") REFERENCES "
                        + RosterItemsCache.TABLE_NAME + "("
                        + RosterItemsCache.FIELD_ID + "),"
                        + "FOREIGN KEY(" + RosterItemsGroupsCache.FIELD_GROUP + ") REFERENCES "
                        + RosterGroupsCache.TABLE_NAME + "("
                        + RosterGroupsCache.FIELD_ID + ")"
                        + ");";
    }

    public static abstract class RosterGroupsCache implements BaseColumns {
        public static final String FIELD_ID = "_id";
        public static final String FIELD_NAME = "name";
        public static final String TABLE_NAME = "roster_groups";

        private static final String CREATE_TABLE =
                "CREATE TABLE "
                        + TABLE_NAME + " ("
                        + FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + FIELD_NAME + " TEXT NOT NULL"
                        + ");";
    }

    public class Geolocation implements BaseColumns {
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.mobilemessenger.geoloc";
        public static final String FIELD_ALT = "alt";
        public static final String FIELD_COUNTRY = "country";
        public static final String FIELD_ID = "_id";
        public static final String FIELD_JID = "jid";
        public static final String FIELD_LAT = "lat";
        public static final String FIELD_LOCALITY = "locality";
        public static final String FIELD_LON = "lon";
        public static final String FIELD_STREET = "street";
        public static final String INDEX_JID = "geolocation_jid_index";
        public static final String TABLE_NAME = "geolocation";


        public static final String CREATE_TABLE = "CREATE TABLE "
                + TABLE_NAME + " ("
                + FIELD_ID + " INTEGER PRIMARY KEY, "
                + FIELD_JID + " TEXT, "
                + FIELD_LON + " REAL, "
                + FIELD_LAT + " REAL, "
                + FIELD_ALT + " REAL, "
                + FIELD_COUNTRY + " TEXT, "
                + FIELD_LOCALITY + " TEXT, "
                + FIELD_STREET + " TEXT "
                + ");";

        public static final String CREATE_INDEX
                = "CREATE INDEX IF NOT EXISTS "
                + INDEX_JID
                + " ON " + TABLE_NAME + " ("
                + FIELD_JID
                + ")";
    }

    public static abstract class VCardsCache implements BaseColumns {
        public static final String FIELD_DATA = "data";
        public static final String FIELD_HASH = "hash";
        public static final String FIELD_ID = "_id";
        public static final String FIELD_JID = "jid";
        public static final String FIELD_TIMESTAMP = "subscription";
        public static final String INDEX_JID = "vcards_cache_jid_index";
        public static final String TABLE_NAME = "vcards_cache";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ("
                        + FIELD_ID + " INTEGER PRIMARY KEY, "
                        + FIELD_JID + " TEXT, "
                        + FIELD_HASH + " TEXT, "
                        + FIELD_DATA + " BLOB, "
                        + FIELD_TIMESTAMP + " DATETIME"
                        + ");";

        public static final String CREATE_INDEX =
                "CREATE INDEX IF NOT EXISTS "
                        + INDEX_JID
                        + " ON " + TABLE_NAME + " ("
                        + FIELD_JID
                        + ")";
        public static final String VCARD_ITEM_TYPE = "vnd.android.cursor.item/vnd.mobilemessenger.vcard";

    }

    public static abstract class RosterItemsCache extends RosterItemsCacheTableMetaData {
        /**
         * Additional field to keep status of buddy from roster item
         * to speed up searches of online users
         * <ul>
         * <li><code>0</code> - unavailable</li>
         * <li><code>1</code> - error</li>
         * <li><code>5</code> - dnd</li>
         * <li><code>10</code> - xa</li>
         * <li><code>15</code> - away</li>
         * <li><code>20</code> - available</li>
         * <li><code>25</code> - chat</li>
         * </ul>
         */
        public static final String FIELD_STATUS = "status";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ("
                        + FIELD_ID + " INTEGER PRIMARY KEY, "
                        + FIELD_ACCOUNT + " TEXT, "
                        + FIELD_JID + " TEXT, "
                        + FIELD_NAME + " TEXT, "
                        + FIELD_ASK + " BOOLEAN, "
                        + FIELD_SUBSCRIPTION + " TEXT, "
                        + FIELD_TIMESTAMP + " DATETIME"
                        + FIELD_STATUS + " INTEGER, "
                        + ");";

        public static final String ROSTER_TYPE = "vnd.android.cursor.dir/vnd.mobilemessenger.roster";
        public static final String ROSTER_ITEM_TYPE = "vnd.android.cursor.item/vnd.mobilemessenger.rosteritem";
    }

    public static abstract class ChatHistory implements BaseColumns {
        public static final String TABLE_NAME = "chat_history";
        public static final String FIELD_ACCOUNT = "account";
        public static final String FIELD_AUTHOR_JID = "author_jid";
        public static final String FIELD_AUTHOR_NICKNAME = "author_nickname";
        public static final String FIELD_BODY = "body";
        public static final String FIELD_DATA = "data";
        public static final String FIELD_ID = "_id";
        public static final String FIELD_JID = "jid";
        /**
         * Description of values for FIELD_STATE (state)
         * <ul>
         * <li><code>0</code> - incoming message</li>
         * <li><code>1</code> - outgoing, not sent</li>
         * <li><code>2</code> - outgoing, sent</li>
         * <li><code>3</code> - incoming unread</li>
         * <li><code>4</code> - incoming locality</li>
         * <li><code>5</code> - outgoing locality</li>
         * </ul>
         */
        public static final String FIELD_STATE = "state";
        public static final String FIELD_THREAD_ID = "thread_id";
        public static final String FIELD_TIMESTAMP = "timestamp";
        /**
         * Description of values for FIELD_ITEM_TYPE (item_type)
         * <ul>
         * <li><code>0</code> - message</li>
         * <li><code>1</code> - locality</li>
         * <li><code>2</code> - file</li>
         * <li><code>3</code> - image</li>
         * <li><code>4</code> - video</li>
         * </ul>
         */
        public static final String FIELD_ITEM_TYPE = "item_type";

        /**
         * index name
         */
        public static final String INDEX_JID = "chat_history_jid_index";

        public static final String CREATE_TABLE =
                "CREATE TABLE "
                        + TABLE_NAME + " ("
                        + FIELD_ID + " INTEGER PRIMARY KEY, "
                        + FIELD_ACCOUNT + " TEXT, "
                        + FIELD_THREAD_ID + " TEXT, "
                        + FIELD_JID + " TEXT, "
                        + FIELD_AUTHOR_JID + " TEXT, "
                        + FIELD_AUTHOR_NICKNAME + " TEXT, "
                        + FIELD_TIMESTAMP + " DATETIME, "
                        + FIELD_BODY + " TEXT, "
                        + FIELD_ITEM_TYPE + " INTEGER, "
                        + FIELD_DATA + " TEXT, "
                        + FIELD_STATE + " INTEGER"
                        + ");";
        public static final String CREATE_INDEX =
                "CREATE INDEX IF NOT EXISTS "
                        + INDEX_JID
                        + " ON " + TABLE_NAME
                        + " ("
                        + FIELD_JID
                        + ")";
    }


}