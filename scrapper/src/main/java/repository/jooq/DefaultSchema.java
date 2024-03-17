/*
 * This file is generated by jOOQ.
 */
package repository.jooq;


import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import repository.jooq.tables.GithubLinks;
import repository.jooq.tables.Links;
import repository.jooq.tables.Sites;
import repository.jooq.tables.StackoverflowLinks;
import repository.jooq.tables.UserLinks;
import repository.jooq.tables.Users;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class DefaultSchema extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>DEFAULT_SCHEMA</code>
     */
    public static final DefaultSchema DEFAULT_SCHEMA = new DefaultSchema();

    /**
     * The table <code>GITHUB_LINKS</code>.
     */
    public final GithubLinks GITHUB_LINKS = GithubLinks.GITHUB_LINKS;

    /**
     * The table <code>LINKS</code>.
     */
    public final Links LINKS = Links.LINKS;

    /**
     * The table <code>SITES</code>.
     */
    public final Sites SITES = Sites.SITES;

    /**
     * The table <code>STACKOVERFLOW_LINKS</code>.
     */
    public final StackoverflowLinks STACKOVERFLOW_LINKS = StackoverflowLinks.STACKOVERFLOW_LINKS;

    /**
     * The table <code>USER_LINKS</code>.
     */
    public final UserLinks USER_LINKS = UserLinks.USER_LINKS;

    /**
     * The table <code>USERS</code>.
     */
    public final Users USERS = Users.USERS;

    /**
     * No further instances allowed
     */
    private DefaultSchema() {
        super("", null);
    }


    @Override
    @NotNull
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    @NotNull
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            GithubLinks.GITHUB_LINKS,
            Links.LINKS,
            Sites.SITES,
            StackoverflowLinks.STACKOVERFLOW_LINKS,
            UserLinks.USER_LINKS,
            Users.USERS
        );
    }
}
