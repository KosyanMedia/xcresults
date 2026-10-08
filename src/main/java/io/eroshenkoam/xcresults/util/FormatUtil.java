package io.eroshenkoam.xcresults.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

public final class FormatUtil {

    private FormatUtil(){
    }

    public static String getResultFileName() {
        final String uuid = UUID.randomUUID().toString();
        return String.format("%s-result.json", uuid);
    }

    public static String getAttachmentFileName(final String fileExtension) {
        final String uuid = UUID.randomUUID().toString();
        return String.format("%s-attachment.%s", uuid, fileExtension);
    }

    public static Path getResultFilePath(final Path outputDir) {
        return outputDir.resolve(getResultFileName());
    }

    private static final String VALUES = "_values";
    private static final ArrayNode EMPTY_ARRAY = JsonNodeFactory.instance.arrayNode();

    /**
     * Safely extract _values from an xcresult array node.
     * In Xcode 27+, empty arrays lack the _values key entirely.
     */
    public static JsonNode getValues(final JsonNode node) {
        if (node == null || !node.has(VALUES)) {
            return EMPTY_ARRAY;
        }
        return node.get(VALUES);
    }

    @SuppressWarnings("PMD.SimpleDateFormatNeedsLocale")
    public static Long parseDate(final String date) {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        try {
            return format.parse(date).getTime();
        } catch (ParseException e) {
            return null;
        }
    }

}
