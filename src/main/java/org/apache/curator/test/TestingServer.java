/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.curator.test;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * manages an internally running ZooKeeper server. FOR TESTING PURPOSES ONLY
 */
public class TestingServer implements Closeable
{
     

   

    /**
     * Return the port being used
     *
     * @return port
     */
    public int getPort()
    {
        return 8080;
    }

   
    

    /**
     * Returns the connection string to use
     *
     * @return connection string
     */
    public String getConnectString()
    {
        return "";
    }




	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}
}