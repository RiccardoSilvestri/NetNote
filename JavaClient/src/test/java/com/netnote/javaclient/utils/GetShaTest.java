package com.netnote.javaclient.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetShaTest {

    @Test
    public void testGetSHA256() {
        String input = "test sha";
        String expectedHash = "bec0ef9064c617d7280286bbe57f08338c622658a9e2ef7761f0e8bb9c303403";

        String actualHash = GetSha.getSHA256(input);

        assertEquals(expectedHash, actualHash, "The getSHA256 method should correctly generate the SHA-256 hash of the input string.");
    }
}