package com.novibe.common.base_dto;

import lombok.Builder;

@Builder
public record DnsProfile(String dnsProvider, String clientId, String authSecret, int number) {
}