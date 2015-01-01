/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.kuujo.copycat;

import net.kuujo.copycat.cluster.coordinator.CoordinatedResourceConfig;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Copycat resource context.
 *
 * @author <a href="http://github.com/kuujo">Jordan Halterman</a>
 */
public interface ResourceContext extends Managed<ResourceContext> {

  /**
   * Returns the resource name.
   *
   * @return The resource name.
   */
  String name();

  /**
   * Returns the resource configuration.
   *
   * @return The resource configuration.
   */
  CoordinatedResourceConfig config();

  /**
   * Returns a list of resource partition contexts.
   *
   * @return A list of resource partition contexts.
   */
  List<ResourcePartitionContext> partitions();

  /**
   * Returns a partition context for the given partition number.
   *
   * @param partition The resource partition number.
   * @return The resource partition context.
   */
  ResourcePartitionContext partition(int partition);

  /**
   * Deletes the resource.
   *
   * @return A completable future to be completed once the resource has been deleted.
   */
  CompletableFuture<Void> delete();

}