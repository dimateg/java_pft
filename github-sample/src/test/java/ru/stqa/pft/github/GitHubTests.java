package ru.stqa.pft.github;

import com.google.common.collect.ImmutableBiMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GitHubTests {

    @Test
    public void testCommits() throws IOException {
        Github github = new RtGithub("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");
        RepoCommits commits = github.repos().get(new Coordinates.Simple("dimateg", "java_pft")).commits();
        for (RepoCommit commit: commits.iterate(new ImmutableBiMap.Builder<String, String>().build())) {
            System.out.println(new RepoCommit.Smart(commit).message());
        }
    }
}