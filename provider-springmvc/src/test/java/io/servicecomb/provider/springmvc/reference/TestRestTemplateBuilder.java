/*
 * Copyright 2017 Huawei Technologies Co., Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.servicecomb.provider.springmvc.reference;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import com.seanyinx.github.unit.scaffolding.Randomness;
import java.net.URI;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class TestRestTemplateBuilder {

  private final String url = Randomness.uniquify("url");
  private final AcceptableRestTemplate underlying = new AlwaysAcceptableRestTemplate();

  private static class AlwaysAcceptableRestTemplate extends AcceptableRestTemplate {

        @Override
        boolean isAcceptable(String uri) {
            return true;
        }

        @Override
        boolean isAcceptable(URI uri) {
            return true;
        }
    }

  @Test
  public void addsRestTemplateToWrapper() {
    RestTemplateBuilder.addAcceptableRestTemplate(underlying);

    RestTemplate restTemplate = RestTemplateBuilder.create();

    assertThat(restTemplate, instanceOf(RestTemplateWrapper.class));

    RestTemplateWrapper wrapper = (RestTemplateWrapper) restTemplate;

    assertThat(wrapper.getRestTemplate(url), is(underlying));
    assertThat(wrapper.getRestTemplate(URI.create(url)), is(underlying));
  }
}
