package edu.java.repository;

import edu.java.model.info.GithubLinkInfo;

public abstract class GithubLinkRepository {
    public abstract GithubLinkInfo updateLink(GithubLinkInfo linkInfo);
}
