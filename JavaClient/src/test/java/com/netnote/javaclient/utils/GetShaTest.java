package com.netnote.javaclient.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Get expected result from: https://emn178.github.io/online-tools/sha256.html
class GetShaTest {

    @Test
    public void testGetSHA256_short() {
        String input = "test sha";
        String expectedHash = "bec0ef9064c617d7280286bbe57f08338c622658a9e2ef7761f0e8bb9c303403";

        String actualHash = GetSha.getSHA256(input);

        assertEquals(expectedHash, actualHash, "The getSHA256 method should correctly generate the SHA-256 hash of the input string.");
    }
    @Test
    public void testGetSHA256_long() {
        String input = "DLIJWSNFLSjfkdldls ojrelknmd.fnjd,sndlkfsj5555md9032urjIUKNSOàà#############2212432rjo9aisuglkh43298ntoh34p92w8j42pt98we";
        String expectedHash = "b5c41e09b9ce796d29bfbb49a3a03a27c5ac43c2b053ca786038cd65ed853e06";

        String actualHash = GetSha.getSHA256(input);

        assertEquals(expectedHash, actualHash, "The getSHA256 method should correctly generate the SHA-256 hash of the input string.");
    }
    @Test
    public void testGetSHA256_loremipsum() {
        String input = """
                Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia,
                molestiae quas vel sint commodi repudiandae consequuntur voluptatum laborum
                numquam blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium
                optio, eaque rerum! Provident similique accusantium nemo autem. Veritatis
                obcaecati tenetur iure eius earum ut molestias architecto voluptate aliquam
                nihil, eveniet aliquid culpa officia aut! Impedit sit sunt quaerat, odit,
                tenetur error, harum nesciunt ipsum debitis quas aliquid. Reprehenderit,
                quia. Quo neque error repudiandae fuga? Ipsa laudantium molestias eos
                sapiente officiis modi at sunt excepturi expedita sint? Sed quibusdam
                recusandae alias error harum maxime adipisci amet laborum. Perspiciatis
                minima nesciunt dolorem! Officiis iure rerum voluptates a cumque velit
                quibusdam sed amet tempora. Sit laborum ab, eius fugit doloribus tenetur
                fugiat, temporibus enim commodi iusto libero magni deleniti quod quam
                consequuntur! Commodi minima excepturi repudiandae velit hic maxime
                doloremque. Quaerat provident commodi consectetur veniam similique ad
                earum omnis ipsum saepe, voluptas, hic voluptates pariatur est explicabo
                fugiat, dolorum eligendi quam cupiditate excepturi mollitia maiores labore
                suscipit quas? Nulla, placeat. Voluptatem quaerat non architecto ab laudantium
                modi minima sunt esse temporibus sint culpa, recusandae aliquam numquam
                totam ratione voluptas quod exercitationem fuga. Possimus quis earum veniam
                quasi aliquam eligendi, placeat qui corporis!""";
        String expectedHash = "56cd4633938275f0c1d4540d1404832568c86dc7a9ed2baff620fdedbc9e1b84";

        String actualHash = GetSha.getSHA256(input);

        assertEquals(expectedHash, actualHash, "The getSHA256 method should correctly generate the SHA-256 hash of the input string.");
    }
}