/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package org.example;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(5)
@State(Scope.Benchmark)
public class CollectionInitializationBenchmark {

    public Set<Integer> set;
    public List<Integer> linkedList;
    public List<Integer> arrayList;
    private static final int COUNT = 1024;

    @Setup
    public void setup() {
        Random random = new Random(22211122);
        set = new HashSet<>();
        linkedList = new LinkedList<>();
        arrayList = new ArrayList<>();

        random.ints(COUNT).boxed().forEach(set::add);
        random.ints(COUNT).boxed().forEach(linkedList::add);
        random.ints(COUNT).boxed().forEach(arrayList::add);
    }

    @Benchmark
    public void createLinkedListFromSet() {
        new LinkedList<>(set);
    }

    @Benchmark
    public void createArrayListFromSet() {
        new ArrayList<>(set);
    }

    @Benchmark
    public void createArrayListFromSetIteratively() {
        ArrayList<Integer> list = new ArrayList<>(set);
        for (Integer integer : set) {
            list.add(integer);
        }
    }

    @Benchmark
    public void createHashSetFromSet() {
        new HashSet<>(set);
    }


    @Benchmark
    public void createLinkedListFromLinkedList() {
        new LinkedList<>(linkedList);
    }

    @Benchmark
    public void createLinkedListFromLinkedListIteratively() {
        List<Integer> list = new LinkedList<>(set);
        for (Integer integer : linkedList) {
            list.add(integer);
        }
    }

    @Benchmark
    public void createArrayListFromLinkedList() {
        new ArrayList<>(linkedList);
    }

    @Benchmark
    public void createArrayListFromLinkedListIteratively() {
        List<Integer> list = new ArrayList<>();
        for (Integer integer : linkedList) {
            list.add(integer);
        }
    }

    @Benchmark
    public void createHashSetFromLinkedList() {
        new HashSet<>(linkedList);
    }


    @Benchmark
    public void createLinkedListFromArrayList() {
        new LinkedList<>(arrayList);
    }

    @Benchmark
    public void createLinkedListFromArrayListIteratively() {
        LinkedList<Integer> list = new LinkedList<>();
        for (Integer integer : arrayList) {
            list.add(integer);
        }
    }

    @Benchmark
    public void createArrayListFromArrayList() {
        new ArrayList<>(arrayList);
    }

    @Benchmark
    public void createArrayListFromArrayListIteratively() {
        ArrayList<Integer> list = new ArrayList<>();
        for (Integer integer : arrayList) {
            list.add(integer);
        }
    }

    @Benchmark
    public void createHashSetFromArrayList() {
        new HashSet<>(arrayList);
    }

    /*

        # Run complete. Total time: 00:12:05

        Benchmark                                                                    Mode  Cnt      Score      Error  Units
        CollectionInitializationBenchmark.createArrayListFromArrayList               avgt   25    471.921 ±    8.582  ns/op
        CollectionInitializationBenchmark.createArrayListFromArrayListIteratively    avgt   25   6402.484 ±   45.530  ns/op
        CollectionInitializationBenchmark.createArrayListFromLinkedList              avgt   25   5557.429 ±   67.428  ns/op
        CollectionInitializationBenchmark.createArrayListFromLinkedListIteratively   avgt   25   9882.569 ±   68.410  ns/op
        CollectionInitializationBenchmark.createArrayListFromSet                     avgt   25   6408.257 ±  559.794  ns/op
        CollectionInitializationBenchmark.createArrayListFromSetIteratively          avgt   25  23772.148 ± 2069.498  ns/op

        CollectionInitializationBenchmark.createHashSetFromArrayList                 avgt   25  21150.606 ±  157.327  ns/op
        CollectionInitializationBenchmark.createHashSetFromLinkedList                avgt   25  19794.106 ±  175.882  ns/op
        CollectionInitializationBenchmark.createHashSetFromSet                       avgt   25  31806.459 ±  238.937  ns/op

        CollectionInitializationBenchmark.createLinkedListFromArrayList              avgt   25   4235.235 ±   30.930  ns/op
        CollectionInitializationBenchmark.createLinkedListFromArrayListIteratively   avgt   25   9160.453 ±   74.417  ns/op
        CollectionInitializationBenchmark.createLinkedListFromLinkedList             avgt   25   8206.192 ±   65.569  ns/op
        CollectionInitializationBenchmark.createLinkedListFromLinkedListIteratively  avgt   25  18493.796 ±  157.669  ns/op
        CollectionInitializationBenchmark.createLinkedListFromSet                    avgt   25   8439.844 ±  101.106  ns/op

     */

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(".*" + CollectionInitializationBenchmark.class.getSimpleName() + ".*").build();

        new Runner(opt).run();
    }
}
