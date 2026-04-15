package com.novibe.common.data_sources;

import com.novibe.common.util.DataParser;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class HostsOverrideListsLoader extends ListLoader<HostsOverrideListsLoader.BypassRoute> {

    public record BypassRoute(String ip, String website) {
    }

    @Override
    protected String listType() {
        return "Override";
    }

    @Override
    protected Predicate<String> filterRelatedLines() {
        return line -> {
            if (HostsBlockListsLoader.isBlock(line)) {
                return false;
            }

            String[] parts = line.strip().split("\\s+");
            return parts.length >= 2
                    && !parts[0].isBlank()
                    && !parts[1].isBlank();
        };
    }

    @Override
    protected BypassRoute toObject(String line) {
        String[] parts = line.strip().split("\\s+");

        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid override line: " + line);
        }

        String ip = parts[0];
        String website = DataParser.removeWWW(parts[1].strip());
        return new BypassRoute(ip, website);
    }

}
