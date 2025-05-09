package com.ea_framework.Model;

public record BatchRequest(String searchSpace,
                           String problem,
                            String algorithm,
                           int iteration,
                           int repeats)
{
}
