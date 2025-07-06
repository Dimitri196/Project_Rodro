import React from 'react';

const About = () => {
  return (
    <div className="max-w-4xl mx-auto px-4 py-10 text-justify leading-relaxed">
      <div className="text-center my-6">
        <h2 className="text-3xl font-semibold">Welcome to the Familia Familiarum Project</h2>
        <p className="italic text-lg mt-2">A Living Archive of People, Places, and Relationships Across Time</p>
      </div>

      <p className="mt-6">
        This platform is the result of a passionate and collaborative effort to document the rich human landscape of a specific region and beyond. At its heart, the <strong>Familia Familiarum</strong> project — Latin for “Family of Families” — is a genealogical and historical database, designed not only for family researchers but also for scholars and enthusiasts in fields such as <em>sociology, anthropology, history, cultural studies, and demography</em>.
      </p>

      <p className="mt-4">
        What began as a personal genealogical journey has grown into a collective mission: to preserve memory, map relationships, and connect data points across generations. This project recognizes that every person’s story is part of a larger human story — shaped by place, social roles, institutions, and historical events.
      </p>

      <p className="mt-4">
        Our database includes more than just people and family ties. It integrates a wide range of historical and sociological contexts:
      </p>

      <ul className="list-disc ml-6 mt-2 space-y-1">
        <li><strong>People & Families:</strong> Genealogical connections across generations.</li>
        <li><strong>Institutions & Occupations:</strong> Social structure, professions, and public roles.</li>
        <li><strong>Places:</strong> Countries, districts, parishes, subdivisions, and cemeteries — geography as heritage.</li>
        <li><strong>Military Service:</strong> Military roles, structures, and historical ranks.</li>
        <li><strong>Taxation & Sources:</strong> Economic and archival records as lenses on society.</li>
      </ul>

      <p className="mt-4">
        By combining these elements, <strong>Familia Familiarum</strong> offers a multidimensional view of communities — not just who people were, but how they lived, worked, worshipped, migrated, and related to each other.
      </p>

      <p className="mt-4">
        This is a non-profit, open-access initiative built on the open-source GeneWeb platform, enhanced to reflect our vision. We invite collaboration, data sharing, and community participation. Whether you’re tracing your ancestry, conducting academic research, or simply curious about the past, we welcome you.
      </p>

      <p className="mt-4">
        Together, let’s preserve history and explore the intricate fabric of human life — one name, one place, one story at a time.
      </p>

      <p className="mt-4 font-semibold">
        (Explore the database using the arrow in the top-right corner or start from the homepage.)
      </p>
    </div>
  );
};

export default About;
