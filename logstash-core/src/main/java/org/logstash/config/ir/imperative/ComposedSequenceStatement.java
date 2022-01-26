/*
 * Licensed to Elasticsearch B.V. under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch B.V. licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


package org.logstash.config.ir.imperative;

import org.logstash.config.ir.InvalidIRException;
import org.logstash.common.SourceWithMetadata;
import org.logstash.config.ir.graph.Graph;
import org.logstash.plugins.ConfigVariableExpander;

import java.util.List;

public class ComposedSequenceStatement extends ComposedStatement {
    public ComposedSequenceStatement(SourceWithMetadata meta, List<Statement> statements) throws InvalidIRException {
        super(meta, statements);
    }

    @Override
    protected String composeTypeString() {
        return "do-sequence";
    }

    @Override
    public Graph toGraph(ConfigVariableExpander cve) throws InvalidIRException {
        Graph g = Graph.empty();

        for (Statement statement : getStatements()) {
            Graph sg = statement.toGraph(cve);
            g = g.chain(sg);
        }

        return g;
    }
}
