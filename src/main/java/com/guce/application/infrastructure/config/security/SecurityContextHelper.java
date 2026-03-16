package com.guce.application.infrastructure.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Utility class providing static helper methods to extract information
 * from the Spring Security context (backed by a Keycloak JWT token).
 */
public final class SecurityContextHelper {

    private SecurityContextHelper() {
        // Utility class - prevent instantiation
    }

    /**
     * Returns the current user's ID (JWT subject claim) as UUID.
     *
     * @return the user ID from the JWT subject, or null if not authenticated
     */
    public static UUID getCurrentUserId() {
        Jwt jwt = getCurrentJwt();
        return jwt != null && jwt.getSubject() != null ? UUID.fromString(jwt.getSubject()) : null;
    }

    /**
     * Returns the current user's email address (from JWT email claim).
     *
     * @return the user's email, or null if not available
     */
    public static String getCurrentUserEmail() {
        Jwt jwt = getCurrentJwt();
        return jwt != null ? jwt.getClaimAsString("email") : null;
    }

    /**
     * Returns the list of roles/authorities granted to the current user.
     *
     * @return list of role strings, or an empty list if not authenticated
     */
    public static List<String> getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return Collections.emptyList();
        }
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    /**
     * Checks whether the current user has the specified role.
     *
     * @param role the role to check (e.g. "ROLE_IMPORTER")
     * @return true if the user has the role, false otherwise
     */
    public static boolean hasRole(String role) {
        return getCurrentUserRoles().contains(role);
    }

    /**
     * Extracts the JWT token from the current security context.
     *
     * @return the Jwt object, or null if no JWT authentication is present
     */
    private static Jwt getCurrentJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt;
        }
        return null;
    }
}
