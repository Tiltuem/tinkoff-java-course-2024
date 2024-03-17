/*
 * This file is generated by jOOQ.
 */
package repository.jooq;


import javax.annotation.processing.Generated;

import repository.jooq.tables.GithubLinks;
import repository.jooq.tables.Links;
import repository.jooq.tables.Sites;
import repository.jooq.tables.StackoverflowLinks;
import repository.jooq.tables.UserLinks;
import repository.jooq.tables.Users;


/**
 * Convenience access to all tables in the default schema.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Tables {

    /**
     * The table <code>GITHUB_LINKS</code>.
     */
    public static final GithubLinks GITHUB_LINKS = GithubLinks.GITHUB_LINKS;

    /**
     * The table <code>LINKS</code>.
     */
    public static final Links LINKS = Links.LINKS;

    /**
     * The table <code>SITES</code>.
     */
    public static final Sites SITES = Sites.SITES;

    /**
     * The table <code>STACKOVERFLOW_LINKS</code>.
     */
    public static final StackoverflowLinks STACKOVERFLOW_LINKS = StackoverflowLinks.STACKOVERFLOW_LINKS;

    /**
     * The table <code>USER_LINKS</code>.
     */
    public static final UserLinks USER_LINKS = UserLinks.USER_LINKS;

    /**
     * The table <code>USERS</code>.
     */
    public static final Users USERS = Users.USERS;
}
