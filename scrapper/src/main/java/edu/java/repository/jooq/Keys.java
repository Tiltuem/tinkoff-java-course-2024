/*
 * This file is generated by jOOQ.
 */

package edu.java.repository.jooq;

import edu.java.repository.jooq.tables.GithubLinks;
import edu.java.repository.jooq.tables.Links;
import edu.java.repository.jooq.tables.Sites;
import edu.java.repository.jooq.tables.StackoverflowLinks;
import edu.java.repository.jooq.tables.UserLinks;
import edu.java.repository.jooq.tables.Users;
import edu.java.repository.jooq.tables.records.GithubLinksRecord;
import edu.java.repository.jooq.tables.records.LinksRecord;
import edu.java.repository.jooq.tables.records.SitesRecord;
import edu.java.repository.jooq.tables.records.StackoverflowLinksRecord;
import edu.java.repository.jooq.tables.records.UserLinksRecord;
import edu.java.repository.jooq.tables.records.UsersRecord;
import javax.annotation.processing.Generated;
import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in the
 * default schema.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<GithubLinksRecord> CONSTRAINT_3 = Internal.createUniqueKey(GithubLinks.GITHUB_LINKS, DSL.name("CONSTRAINT_3"), new TableField[] { GithubLinks.GITHUB_LINKS.LINK_ID }, true);
    public static final UniqueKey<LinksRecord> CONSTRAINT_45 = Internal.createUniqueKey(Links.LINKS, DSL.name("CONSTRAINT_45"), new TableField[] { Links.LINKS.ID }, true);
    public static final UniqueKey<LinksRecord> CONSTRAINT_451 = Internal.createUniqueKey(Links.LINKS, DSL.name("CONSTRAINT_451"), new TableField[] { Links.LINKS.URL }, true);
    public static final UniqueKey<SitesRecord> CONSTRAINT_4B = Internal.createUniqueKey(Sites.SITES, DSL.name("CONSTRAINT_4B"), new TableField[] { Sites.SITES.ID }, true);
    public static final UniqueKey<SitesRecord> CONSTRAINT_4B4 = Internal.createUniqueKey(Sites.SITES, DSL.name("CONSTRAINT_4B4"), new TableField[] { Sites.SITES.SITE_NAME }, true);
    public static final UniqueKey<StackoverflowLinksRecord> CONSTRAINT_1 = Internal.createUniqueKey(StackoverflowLinks.STACKOVERFLOW_LINKS, DSL.name("CONSTRAINT_1"), new TableField[] { StackoverflowLinks.STACKOVERFLOW_LINKS.LINK_ID }, true);
    public static final UniqueKey<UserLinksRecord> CONSTRAINT_C67 = Internal.createUniqueKey(UserLinks.USER_LINKS, DSL.name("CONSTRAINT_C67"), new TableField[] { UserLinks.USER_LINKS.USER_ID, UserLinks.USER_LINKS.LINK_ID }, true);
    public static final UniqueKey<UsersRecord> CONSTRAINT_4 = Internal.createUniqueKey(Users.USERS, DSL.name("CONSTRAINT_4"), new TableField[] { Users.USERS.ID }, true);
    public static final UniqueKey<UsersRecord> CONSTRAINT_4D = Internal.createUniqueKey(Users.USERS, DSL.name("CONSTRAINT_4D"), new TableField[] { Users.USERS.CHAT_ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<GithubLinksRecord, LinksRecord> CONSTRAINT_3A = Internal.createForeignKey(GithubLinks.GITHUB_LINKS, DSL.name("CONSTRAINT_3A"), new TableField[] { GithubLinks.GITHUB_LINKS.LINK_ID }, Keys.CONSTRAINT_45, new TableField[] { Links.LINKS.ID }, true);
    public static final ForeignKey<LinksRecord, SitesRecord> FK = Internal.createForeignKey(Links.LINKS, DSL.name("FK"), new TableField[] { Links.LINKS.SITE_ID }, Keys.CONSTRAINT_4B, new TableField[] { Sites.SITES.ID }, true);
    public static final ForeignKey<StackoverflowLinksRecord, LinksRecord> CONSTRAINT_1C = Internal.createForeignKey(StackoverflowLinks.STACKOVERFLOW_LINKS, DSL.name("CONSTRAINT_1C"), new TableField[] { StackoverflowLinks.STACKOVERFLOW_LINKS.LINK_ID }, Keys.CONSTRAINT_45, new TableField[] { Links.LINKS.ID }, true);
    public static final ForeignKey<UserLinksRecord, UsersRecord> CONSTRAINT_C = Internal.createForeignKey(UserLinks.USER_LINKS, DSL.name("CONSTRAINT_C"), new TableField[] { UserLinks.USER_LINKS.USER_ID }, Keys.CONSTRAINT_4, new TableField[] { Users.USERS.ID }, true);
    public static final ForeignKey<UserLinksRecord, LinksRecord> CONSTRAINT_C6 = Internal.createForeignKey(UserLinks.USER_LINKS, DSL.name("CONSTRAINT_C6"), new TableField[] { UserLinks.USER_LINKS.LINK_ID }, Keys.CONSTRAINT_45, new TableField[] { Links.LINKS.ID }, true);
}
