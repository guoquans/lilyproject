/*
 * Copyright 2013 NGDATA nv
 * Copyright 2008 Outerthought bvba and Schaubroeck nv
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lilyproject.runtime.repository;

import java.io.File;
import java.util.Collections;
import java.util.regex.Matcher;

public class Maven2StyleArtifactRepository extends BaseArtifactRepository {
    private final File repositoryLocation;
    private final String sep = System.getProperty("file.separator");

    public Maven2StyleArtifactRepository(File repositoryLocation) {
        this.repositoryLocation = repositoryLocation;
    }

    public ResolvedArtifact tryResolve(String groupId, String artifactId, String classifier, String version) throws ArtifactNotFoundException {
        String groupPath = groupId.replaceAll("\\.", Matcher.quoteReplacement(sep));
        String classifierSuffix = classifier == null || classifier.length() == 0 ? "" : "-" + classifier;
        File artifactFile = new File(repositoryLocation, groupPath + sep + artifactId + sep + version + sep + artifactId + "-" + version + classifierSuffix + ".jar");
        return new ResolvedArtifact(artifactFile, Collections.singletonList(artifactFile.getAbsolutePath()), artifactFile.exists());
    }
}
