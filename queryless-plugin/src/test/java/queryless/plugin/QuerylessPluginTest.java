package queryless.plugin;

import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.PlexusTestCase;

public class QuerylessPluginTest extends PlexusTestCase {

    protected void setUp() throws Exception {
        super.setUp();
        PlexusContainer container = lookup(PlexusContainer.class);
    }

    public void testDirectoryScan() {

    }

}