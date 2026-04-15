package com.novibe.common.data_sources;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class HostsOverrideListsLoader extends ListLoader<HostsOverrideListsLoader.BypassRoute> {

    public record BypassRoute(String ip, String website) {}

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

            // Оставляем только строки, где есть хотя бы 2 токена: ip и domain
            String[] parts = line.strip().split("\\s+");
            return parts.length >= 2;
        };
    }

    @Override
    protected BypassRoute toObject(String line) {
        String[] parts = line.strip().split("\\s+");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid override line: " + line);
        }

        String ip = parts[0];
        String website = removeWWW(parts[1].strip());
        return new BypassRoute(ip, website);
    }
}
