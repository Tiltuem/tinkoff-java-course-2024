/*
 * This file is generated by jOOQ.
 */

package edu.java.repository.jooq.tables.pojos;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


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
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long chatId;

    public Users() {}

    public Users(Users value) {
        this.id = value.id;
        this.chatId = value.chatId;
    }

    @ConstructorProperties({ "id", "chatId" })
    public Users(
        @Nullable Long id,
        @NotNull Long chatId
    ) {
        this.id = id;
        this.chatId = chatId;
    }

    /**
     * Getter for <code>USERS.ID</code>.
     */
    @Nullable
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>USERS.ID</code>.
     */
    public void setId(@Nullable Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>USERS.CHAT_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getChatId() {
        return this.chatId;
    }

    /**
     * Setter for <code>USERS.CHAT_ID</code>.
     */
    public void setChatId(@NotNull Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Users other = (Users) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.chatId == null) {
            if (other.chatId != null)
                return false;
        }
        else if (!this.chatId.equals(other.chatId))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.chatId == null) ? 0 : this.chatId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Users (");

        sb.append(id);
        sb.append(", ").append(chatId);

        sb.append(")");
        return sb.toString();
    }
}
