/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openrewrite.xml;

import org.junit.jupiter.api.Test;
import org.openrewrite.DocumentExample;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.xml.Assertions.xml;

class CreateXmlFileTest implements RewriteTest {

    @DocumentExample
    @Test
    void hasCreatedFile() {
        rewriteRun(
          spec -> spec.recipe(new CreateXmlFile(
            "test/test.xml",
            null
          )),
          xml(
            null,
            "",
            spec -> spec.path("test/test.xml")
          )
        );
    }

    @DocumentExample
    @Test
    void hasOverwrittenFile() {
        rewriteRun(
          spec -> spec.recipe(new CreateXmlFile(
            "test/test.xml",
            true
          )),
          xml(
            """
              <?xml version="1.0" encoding="UTF-8"?>
              <test/>
              """,
            "",
            spec -> spec.path("test/test.xml")
          )
        );
    }

    @Test
    void shouldNotChangeExistingFile() {
        rewriteRun(
          spec -> spec.recipe(new CreateXmlFile(
            "test/test.xml",
            false
          )),
          xml(
            """
              <?xml version="1.0" encoding="UTF-8"?>
              <test/>
              """,
            spec -> spec.path("test/test.xml")
          )
        );
    }

    @Test
    void shouldNotChangeExistingFileWhenOverwriteNull() {
        rewriteRun(
          spec -> spec.recipe(new CreateXmlFile(
            "test/test.xml",
            null
          )),
          xml(
            """
              <?xml version="1.0" encoding="UTF-8"?>
              <test/>
              """,
            spec -> spec.path("test/test.xml")
          )
        );
    }

    @Test
    void shouldAddAnotherFile() {
        rewriteRun(
          spec -> spec.recipe(new CreateXmlFile(
            "test/test-file-2.xml",
            true
          )),
          xml(
            """
              <?xml version="1.0" encoding="UTF-8"?>
              <test/>
              """,
            spec -> spec.path("test/test-file-1.xml")
          ),
          xml(
            null,
            "",
            spec -> spec.path("test/test-file-2.xml")
          )
        );
    }
}
