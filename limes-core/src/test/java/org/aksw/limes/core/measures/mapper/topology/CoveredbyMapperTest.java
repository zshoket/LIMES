package org.aksw.limes.core.measures.mapper.topology;

import static org.junit.Assert.assertTrue;

import org.aksw.limes.core.io.cache.ACache;
import org.aksw.limes.core.io.cache.MemoryCache;
import org.aksw.limes.core.io.mapping.AMapping;
import org.aksw.limes.core.io.mapping.MappingFactory;
import org.aksw.limes.core.measures.mapper.AMapper;
import org.junit.Test;

/**
 * @author Kevin Dreßler
 * @since 1.0
 */
public class CoveredbyMapperTest {

    @Test
    public void testGetMapping() {
        ACache s = new MemoryCache();
        s.addTriple("http://test.com/s/#1", "asWKT", "POLYGON ((0 0, 0 10, 10 10, 10 0, 0 0))");
        s.addTriple("http://test.com/s/#2", "asWKT", "POLYGON ((-10 -10, 0 10, 10 10, 10 0, -10 -10))");
        ACache t = new MemoryCache();
        t.addTriple("http://test.com/t/#1", "asWKT", "POLYGON ((-1 -1, -1 11, 11 11, 11 -1, -1 -1))");
        AMapping referenceMapping = MappingFactory.createMapping(MappingFactory.MappingType.MEMORY_MAPPING);
        referenceMapping.add("http://test.com/s/#1", "http://test.com/t/#1", 1.0d);
        AMapper mapper = new CoveredbyMapper();
        assertTrue("Expect mapping generated by CoveredbyMapper to be equal to reference mapping",
                mapper.getMapping(s, t, "?x", "?y", "within(x.asWKT, y.asWKT)", 1.0d).equals(referenceMapping));
    }
}