/*
 * The MIT License (MIT) Copyright (c) 2020-2022 artipie.com
 * https://github.com/artipie/rpm-adapter/LICENSE.txt
 */
package com.artipie.rpm;

import com.amihaiemil.eoyaml.Yaml;
import java.util.Optional;
import org.cactoos.list.ListOf;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.AllOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Satisfies;

/**
 * Test for {@link RepoConfig.FromYaml}.
 * @since 0.10
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class RepoConfigFromYamlTest {

    @Test
    void readsSettings() {
        MatcherAssert.assertThat(
            new RepoConfig.FromYaml(
                Yaml.createYamlMappingBuilder().add("digest", "sha1")
                .add("naming-policy", "sha256").add("filelists", "false")
                .add("update", Yaml.createYamlMappingBuilder().add("on", "upload").build()).build()
            ),
            new AllOf<>(
                new ListOf<Matcher<? super RepoConfig>>(
                    new Satisfies<>(cfg -> cfg.digest() == Digest.SHA1),
                    new Satisfies<>(cfg -> cfg.naming() == StandardNamingPolicy.SHA256),
                    new Satisfies<>(fromYaml -> !fromYaml.filelists()),
                    new Satisfies<>(cfg -> cfg.mode() == RepoConfig.UpdateMode.UPLOAD),
                    new Satisfies<>(cfg -> !cfg.cron().isPresent())
                )
            )
        );
    }

    @Test
    void readsSettingsWithCron() {
        final String cron = "0 * * * *";
        MatcherAssert.assertThat(
            new RepoConfig.FromYaml(
                Yaml.createYamlMappingBuilder()
                    .add(
                        "update",
                        Yaml.createYamlMappingBuilder().add(
                            "on",
                            Yaml.createYamlMappingBuilder().add("cron", cron).build()
                        ).build()
                    ).build()
            ),
            new AllOf<>(
                new ListOf<Matcher<? super RepoConfig>>(
                    new Satisfies<>(cfg -> cfg.mode() == RepoConfig.UpdateMode.CRON),
                    new Satisfies<>(cfg -> cfg.cron().get().equals(cron))
                )
            )
        );
    }

    @Test
    void returnsDefaults() {
        MatcherAssert.assertThat(
            new RepoConfig.FromYaml(Optional.empty()),
            new AllOf<>(
                new ListOf<Matcher<? super RepoConfig>>(
                    new Satisfies<>(cfg -> cfg.digest() == Digest.SHA256),
                    new Satisfies<>(cfg -> cfg.naming() == StandardNamingPolicy.SHA256),
                    new Satisfies<>(RepoConfig::filelists),
                    new Satisfies<>(cfg -> cfg.mode() == RepoConfig.UpdateMode.UPLOAD),
                    new Satisfies<>(cfg -> !cfg.cron().isPresent())
                )
            )
        );
    }
}
