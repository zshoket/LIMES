package org.aksw.limes.core.measures.mapper.temporal.allenAlgebra;

import static org.junit.Assert.assertTrue;

import org.aksw.limes.core.execution.engine.ExecutionEngine;
import org.aksw.limes.core.execution.engine.SimpleExecutionEngine;
import org.aksw.limes.core.execution.planning.planner.DynamicPlanner;
import org.aksw.limes.core.io.cache.ACache;
import org.aksw.limes.core.io.cache.Instance;
import org.aksw.limes.core.io.cache.MemoryCache;
import org.aksw.limes.core.io.ls.LinkSpecification;
import org.aksw.limes.core.io.mapping.AMapping;
import org.aksw.limes.core.io.mapping.MappingFactory;
import org.aksw.limes.core.measures.measure.temporal.allenAlgebra.StartsMeasure;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StartsMapperTest {


    public ACache source = new MemoryCache();
    public ACache target = new MemoryCache();

    @Before
    public void setUp() {
        source = new MemoryCache();
        target = new MemoryCache();
        // create source cache
        source.addTriple("S1", "beginsAtDateTime", "2015-05-20T08:21:04+02:00");
        source.addTriple("S1", "endsAtDateTime", "2015-05-20T08:22:04+02:00");

        source.addTriple("S2", "beginsAtDateTime", "2015-05-20T08:21:04+02:00");
        source.addTriple("S2", "endsAtDateTime", "2015-05-20T08:22:04+02:00");

        source.addTriple("S3", "beginsAtDateTime", "2015-05-20T08:24:04+02:00");
        source.addTriple("S3", "endsAtDateTime", "2015-05-20T08:25:04+02:00");

        source.addTriple("S4", "beginsAtDateTime", "2015-05-20T08:31:04+02:00");
        source.addTriple("S4", "endsAtDateTime", "2015-05-20T08:32:04+02:00");

        source.addTriple("S5", "beginsAtDateTime", "2015-05-20T09:21:04+02:00");
        source.addTriple("S5", "endsAtDateTime", "2015-05-20T09:24:04+02:00");

        source.addTriple("S6", "beginsAtDateTime", "2015-05-20T08:51:04+02:00");
        source.addTriple("S6", "endsAtDateTime", "2015-05-20T09:24:04+02:00");

        source.addTriple("S7", "beginsAtDateTime", "2015-05-20T08:41:04+02:00");
        source.addTriple("S7", "endsAtDateTime", "2015-05-20T08:51:04+02:00");

        source.addTriple("S8", "beginsAtDateTime", "2015-05-20T08:41:04+02:00");
        source.addTriple("S8", "endsAtDateTime", "2015-05-20T08:43:04+02:00");

        source.addTriple("S9", "beginsAtDateTime", "2015-05-20T08:21:04+02:00");
        source.addTriple("S9", "endsAtDateTime", "2015-05-20T08:34:04+02:00");

        source.addTriple("S10", "beginsAtDateTime", "2015-05-20T09:21:04+02:00");
        source.addTriple("S10", "endsAtDateTime", "2015-05-20T09:22:04+02:00");

        source.addTriple("S11", "beginsAtDateTime", "2015-05-20T09:21:04+02:00");
        source.addTriple("S11", "endsAtDateTime", "2015-05-20T09:22:04+02:00");

        source.addTriple("S12", "beginsAtDateTime", "2015-05-20T08:31:04+02:00");
        source.addTriple("S12", "endsAtDateTime", "2015-05-20T08:45:04+02:00");


        target = source;
    }

    @After
    public void tearDown() {
        source = null;
        target = null;
    }

    @Test
    public void simpleLS() {
        System.out.println("simpleLS");
        LinkSpecification ls = new LinkSpecification(
                "tmp_starts(x.beginsAtDateTime|endsAtDateTime,y.beginsAtDateTime|endsAtDateTime)",
                1.0);
        DynamicPlanner p = new DynamicPlanner(source, target);
        ExecutionEngine e = new SimpleExecutionEngine(source, target, "?x", "?y");
        AMapping m = e.execute(ls, p);
        System.out.println(m);

    }

    @Test
    public void similarity() {
        System.out.println("simpleLS");
        LinkSpecification ls = new LinkSpecification(
                "tmp_starts(x.beginsAtDateTime|endsAtDateTime,y.beginsAtDateTime|endsAtDateTime)",
                1.0);
        DynamicPlanner p = new DynamicPlanner(source, target);
        ExecutionEngine e = new SimpleExecutionEngine(source, target, "?x", "?y");
        AMapping m = e.execute(ls, p);
        System.out.println(m);

        AMapping m2 = MappingFactory.createDefaultMapping();
        for (Instance s : source.getAllInstances()) {
            for (Instance t : target.getAllInstances()) {
                StartsMeasure measure = new StartsMeasure();
                double sim = measure.getSimilarity(s, t, "beginsAtDateTime|endsAtDateTime", "beginsAtDateTime|endsAtDateTime");
                if (sim != 0)
                    m2.add(s.getUri(), t.getUri(), sim);
            }
        }
        assertTrue(m.equals(m2));
    }


}
