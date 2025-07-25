// const API_URL = "http://194.182.86.112:8080";

const API_URL = "http://localhost:8080";

// Custom error for better debugging
export class HttpRequestError extends Error {
  constructor(response) {
    super(`Network response was not ok: ${response.status} ${response.statusText}`);
    this.name = "HttpRequestError";
    this.response = response;
  }
}

const fetchData = (url, requestOptions) => {
  const apiUrl = `${API_URL}${url}`;

  // Always include credentials for cookie/session handling
  const options = {
    ...requestOptions,
    credentials: 'include',
  };

  return fetch(apiUrl, options)
    .then((response) => {
      if (!response.ok) {
        throw new HttpRequestError(response);
      }

      if (options.method !== 'DELETE') {
        return response.json();
      }
    });
};

export const apiGet = (url, params) => {
  const filteredParams = Object.fromEntries(
    Object.entries(params || {}).filter(([_, value]) => value != null)
  );

  const queryString = new URLSearchParams(filteredParams).toString();
  const apiUrl = queryString ? `${url}?${queryString}` : url;

  return fetchData(apiUrl, { method: "GET" });
};

export const apiPost = (url, data) => {
  return fetchData(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });
};

export const apiPut = (url, data) => {
  return fetchData(url, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });
};

export const apiDelete = (url) => {
  return fetchData(url, { method: "DELETE" });
};
