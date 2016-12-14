/*
 * SonarQube :: GitLab Plugin
 * Copyright (C) 2016-2016 Talanlabs
 * gabriel.allaigre@talanlabs.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.synaptix.sonar.plugins.gitlab;

import org.sonar.api.PropertyType;
import org.sonar.api.SonarPlugin;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GitLabPlugin extends SonarPlugin {

    public static final String GITLAB_URL = "sonar.gitlab.url";
    public static final String GITLAB_IGNORE_CERT = "sonar.gitlab.ignore_certificate";
    public static final String GITLAB_MAX_GLOBAL_ISSUES = "sonar.gitlab.max_global_issues";
    public static final String GITLAB_USER_TOKEN = "sonar.gitlab.user_token";
    public static final String GITLAB_PROJECT_ID = "sonar.gitlab.project_id";
    public static final String GITLAB_REF_NAME = "sonar.gitlab.ref_name";
    public static final String GITLAB_IGNORE_FILE = "sonar.gitlab.ignore_file";

    public static final String CATEGORY = "gitlab";
    public static final String SUBCATEGORY = "reporting";

    private static List<PropertyDefinition> definitions() {
        return Arrays
                .asList(PropertyDefinition.builder(GITLAB_URL).name("GitLab URL").description("URL to access GitLab.").category(CATEGORY).subCategory(SUBCATEGORY).defaultValue("https://gitlab.com")
                                .index(1).build(), PropertyDefinition.builder(GITLAB_IGNORE_CERT).name("GitLab Ignore Certificate").description("Ignore Certificate for access GitLab.").category(CATEGORY)
                                .subCategory(SUBCATEGORY).type(PropertyType.BOOLEAN).defaultValue(String.valueOf(false)).index(2).build(),
                        PropertyDefinition.builder(GITLAB_USER_TOKEN).name("GitLab User Token").description("GitLab user token is developer role.").category(CATEGORY).subCategory(SUBCATEGORY).index(3)
                                .build(), PropertyDefinition.builder(GITLAB_MAX_GLOBAL_ISSUES).name("GitLab Max Global Issues").description("Max issues to show in global comment.").category(CATEGORY)
                                .subCategory(SUBCATEGORY).type(PropertyType.INTEGER).defaultValue(String.valueOf(10)).index(4).build(),
                        PropertyDefinition.builder(GITLAB_PROJECT_ID).name("GitLab Project id")
                                .description("The unique id, path with namespace, name with namespace, web url, ssh url or http url of the current project that GitLab.").category(CATEGORY)
                                .subCategory(SUBCATEGORY).index(5).onlyOnQualifiers(Qualifiers.PROJECT).build(),
                        PropertyDefinition.builder(GITLAB_REF_NAME).name("GitLab Ref Name").description("The branch for which project is built.").category(CATEGORY).subCategory(SUBCATEGORY)
                                .index(6).hidden().build(),
                        PropertyDefinition.builder(GITLAB_IGNORE_FILE).name("GitLab Ingore file").description("Ignore issues on files no modified by the commit").category(CATEGORY)
                                .subCategory(SUBCATEGORY).type(PropertyType.BOOLEAN).defaultValue(String.valueOf(false)).index(7).hidden().build());
    }

    @Override
    public List getExtensions() {
        List extensions = new ArrayList();
        extensions.addAll(Arrays.asList(CommitIssuePostJob.class, GitLabPluginConfiguration.class, CommitProjectBuilder.class, CommitFacade.class, InputFileCacheSensor.class, InputFileCache.class,
                MarkDownUtils.class));
        extensions.addAll(definitions());
        return extensions;
    }
}
