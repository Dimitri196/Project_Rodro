import React from "react";
import { Link, useNavigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

const PrivacyPolicy = () => {
  return (
    <div className="max-w-4xl mx-auto p-6 text-gray-800">
      <h1 className="text-3xl font-bold mb-6">Privacy Policy</h1>
      <p className="italic mb-4">Effective Date: [Insert Date]</p>

      <section className="mb-6">
        <h2 className="text-xl font-semibold mb-2">1. Who We Are</h2>
        <p>
          This website is operated by a private individual based in the Czech Republic.
          For any questions regarding this policy, please contact us at:{" "}
          <span className="font-medium">[your-email@example.com]</span>
        </p>
      </section>

      <section className="mb-6">
        <h2 className="text-xl font-semibold mb-2">2. What Data We Collect</h2>
        <ul className="list-disc list-inside space-y-1">
          <li>Email address (if submitted through the contact form)</li>
          <li>Message content and name (if voluntarily provided)</li>
          <li>IP address and browser information (for diagnostics and security)</li>
        </ul>
      </section>

      <section className="mb-6">
        <h2 className="text-xl font-semibold mb-2">3. How We Use Your Data</h2>
        <p>We use your personal data for the following purposes:</p>
        <ul className="list-disc list-inside space-y-1 mt-2">
          <li>To respond to messages sent via the contact form</li>
          <li>To monitor website performance and maintain security</li>
          <li>To comply with legal obligations if applicable</li>
        </ul>
        <p className="mt-2">
          We do <strong>not</strong> share your data with third parties, nor do we use it for marketing.
        </p>
      </section>

      <section className="mb-6">
        <h2 className="text-xl font-semibold mb-2">4. Legal Basis for Processing</h2>
        <ul className="list-disc list-inside space-y-1">
          <li><strong>Consent</strong> – when you submit information voluntarily</li>
          <li><strong>Legitimate interest</strong> – to operate and protect the website</li>
        </ul>
      </section>

      <section className="mb-6">
        <h2 className="text-xl font-semibold mb-2">5. Cookies and Tracking</h2>
        <p>
          We use only essential cookies required for site functionality. We do not use third-party tracking or analytics cookies. You can disable cookies in your browser settings if desired.
        </p>
      </section>

      <section className="mb-6">
        <h2 className="text-xl font-semibold mb-2">6. Data Retention</h2>
        <p>
          Contact form submissions may be stored for up to 12 months to ensure proper follow-up. Other data is retained only as long as necessary for its intended purpose.
        </p>
      </section>

      <section className="mb-6">
        <h2 className="text-xl font-semibold mb-2">7. Your Rights</h2>
        <p>As an EU resident, you have the right to:</p>
        <ul className="list-disc list-inside space-y-1 mt-2">
          <li>Access the personal data we hold about you</li>
          <li>Request correction or deletion of your data</li>
          <li>Withdraw your consent at any time</li>
          <li>File a complaint with your data protection authority</li>
        </ul>
        <p className="mt-2">
          To exercise your rights, contact us at:{" "}
          <span className="font-medium">[your-email@example.com]</span>
        </p>
      </section>

      <section className="mb-6">
        <h2 className="text-xl font-semibold mb-2">8. Data Security</h2>
        <p>
          We implement reasonable security measures to protect your data from unauthorized access, disclosure, or loss.
        </p>
      </section>

      <section className="mb-6">
        <h2 className="text-xl font-semibold mb-2">9. Changes to This Policy</h2>
        <p>
          This privacy policy may be updated occasionally. The most recent version will always be available at{" "}
          <span className="underline">https://www.familiarum.eu/privacy-policy</span>.
        </p>
      </section>

      <section className="mb-6">
        <h2 className="text-xl font-semibold mb-2">10. Additional Notice for Genealogy Research</h2>
        <p>
          This website is intended for historical and genealogical research. Information about deceased individuals is
          publicly accessible. Data about living individuals is either hidden or included only with explicit consent.
        </p>
      </section>

      <p className="text-sm text-gray-500">
        © {new Date().getFullYear()} familiarum.eu — All rights reserved.
      </p>
    </div>
  );
};

export default PrivacyPolicy;
