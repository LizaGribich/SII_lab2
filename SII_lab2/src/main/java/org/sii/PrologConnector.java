package org.sii;

import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Atom;

import java.io.*;

public class PrologConnector {
    private final String prologFile;

    public PrologConnector(String prologFile) {
        this.prologFile = prologFile;
    }

    public void loadKnowledgeBase() throws IOException {
        InputStream resourceStream = getClass().getResourceAsStream(prologFile);

        File tempFile = File.createTempFile("prolog_kb", ".pl");
        tempFile.deleteOnExit();

        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = resourceStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        resourceStream.close();
        Query consultQuery = new Query("consult", new Term[]{new Atom(tempFile.getAbsolutePath())});

        consultQuery.hasSolution();

    }
}
