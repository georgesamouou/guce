package com.guce.application.adapter.out.attestation;

import com.guce.application.domain.port.out.AttestationGeneratorOutPort;
import com.lowagie.text.DocumentException;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ThymeleafAttestationGenerator implements AttestationGeneratorOutPort {

    private final TemplateEngine templateEngine;

    @Override
    public byte[] generate(Map<String, Object> data) {
        // 1. Render Thymeleaf template to HTML
        Context context = new Context();
        context.setVariables(data);
        String html = templateEngine.process("attestation_fimex", context);

        // 2. Convert HTML to PDF using Flying Saucer
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        } catch (DocumentException e) {
            throw new RuntimeException("Failed to generate attestation PDF", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate attestation PDF", e);
        }
    }
}
