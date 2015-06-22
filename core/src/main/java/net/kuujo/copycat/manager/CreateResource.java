/*
 * Copyright 2015 the original author or authors.
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
package net.kuujo.copycat.manager;

import net.kuujo.alleycat.Alleycat;
import net.kuujo.alleycat.SerializationException;
import net.kuujo.alleycat.SerializeWith;
import net.kuujo.alleycat.io.Buffer;
import net.kuujo.copycat.raft.Command;
import net.kuujo.copycat.raft.Operation;
import net.kuujo.copycat.raft.StateMachine;

/**
 * Create resource command.
 *
 * @author <a href="http://github.com/kuujo">Jordan Halterman</a>
 */
@SerializeWith(id=414)
public class CreateResource extends PathOperation<Long> implements Command<Long> {

  /**
   * Returns a new CreateResource builder.
   *
   * @return A new CreateResource command builder.
   */
  public static Builder builder() {
    return Operation.builder(CreateResource.Builder.class);
  }

  private Class<? extends StateMachine> type;

  public CreateResource() {
  }

  public CreateResource(String path, Class<? extends StateMachine> type) {
    super(path);
    this.type = type;
  }

  /**
   * Returns the resource class.
   *
   * @return The resource class.
   */
  public Class<? extends StateMachine> type() {
    return type;
  }

  @Override
  public void writeObject(Buffer buffer, Alleycat alleycat) {
    super.writeObject(buffer, alleycat);
    buffer.writeInt(type.getName().getBytes().length).write(type.getName().getBytes());
  }

  @Override
  @SuppressWarnings("unchecked")
  public void readObject(Buffer buffer, Alleycat alleycat) {
    super.readObject(buffer, alleycat);
    byte[] bytes = new byte[buffer.readInt()];
    buffer.read(bytes);
    String typeName = new String(bytes);
    try {
      type = (Class<? extends StateMachine>) Class.forName(typeName);
    } catch (ClassNotFoundException e) {
      throw new SerializationException(e);
    }
  }

  /**
   * Create resource builder.
   */
  public static class Builder extends PathOperation.Builder<Builder, CreateResource> {
    @Override
    protected CreateResource create() {
      return new CreateResource();
    }

    /**
     * Sets the command type.
     *
     * @param type The command type.
     * @return The command builder.
     */
    public Builder withType(Class<? extends StateMachine> type) {
      operation.type = type;
      return this;
    }
  }

}