import React from "react";

const TermsOfService = () => {
  return (
    <div className="container py-5">
      <h1 className="mb-4">Terms of Service</h1>

      <p className="mb-3">
        Welcome to <strong>familiarum.eu</strong>. By using this website and its associated services,
        you agree to the following terms and conditions. If you do not agree
        with these terms, please refrain from using the site.
      </p>

      <h2 className="mt-5">1. About the Project</h2>
      <p>
        This website is operated as a personal non-commercial initiative based in the Czech Republic.
        Its purpose is to document and share genealogical, historical, and sociological data about people,
        families, institutions, and regions across time and space. The site is operated by a private
        individual for research and educational purposes.
      </p>

      <h2 className="mt-5">2. Data Accuracy</h2>
      <p>
        While we strive to ensure the accuracy of the information published, no guarantee is made regarding
        the completeness, reliability, or correctness of data. You use the data at your own risk.
      </p>

      <h2 className="mt-5">3. User Contributions</h2>
      <p>
        If you submit information, such as corrections, feedback, or contact forms, you affirm that:
        <ul>
          <li>You are the rightful owner of the content or have permission to share it;</li>
          <li>You grant the operator the right to use, adapt, and include the submitted data within the project database;</li>
          <li>Your submission does not infringe upon the rights of others or violate any applicable laws.</li>
        </ul>
      </p>

      <h2 className="mt-5">4. Intellectual Property</h2>
      <p>
        All website content, including text, design, and data structure, unless otherwise stated, is the intellectual
        property of the operator and may not be reproduced or redistributed without permission. Open data sources,
        if used, are cited where applicable.
      </p>

      <h2 className="mt-5">5. Privacy and Data Handling</h2>
      <p>
        Please refer to our <a href="/privacy-policy">Privacy Policy</a> for information on how personal data is collected,
        used, and protected.
      </p>

      <h2 className="mt-5">6. Disclaimers</h2>
      <p>
        This website and its services are provided “as is” with no warranties, express or implied. The operator does not
        assume responsibility for any damages arising from the use or inability to use the website.
      </p>

      <h2 className="mt-5">7. Governing Law</h2>
      <p>
        These terms shall be governed by and construed in accordance with the laws of the Czech Republic and applicable
        European Union regulations.
      </p>

      <h2 className="mt-5">8. Changes to These Terms</h2>
      <p>
        These Terms of Service may be updated from time to time. Continued use of the website after changes implies acceptance
        of the new terms.
      </p>

      <h2 className="mt-5">9. Contact</h2>
      <p>
        For questions or concerns regarding these terms, please use the contact form available on the website.
      </p>

      <p className="mt-4 text-muted">Last updated: June 29, 2025</p>
    </div>
  );
};

export default TermsOfService;
