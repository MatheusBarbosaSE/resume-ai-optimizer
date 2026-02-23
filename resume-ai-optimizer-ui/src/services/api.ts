import axios from "axios";

// This interface mirrors the Java Record "ResumeResponse"
export interface ResumeResponse {
  targetJobCompanyName: string;
  matchPercentage: number;
  missingKeywords: string[];
  improvementTips: string;
}

// Create an Axios instance pointing to Spring Boot backend
const apiClient = axios.create({
  baseURL: "http://localhost:8080/api/v1",
  // timeout: 120000, // Optional: 2 minutes timeout since local LLM can be slow
});

/**
 * Sends the PDF and Job Description to the Java API for AI analysis.
 * * @param pdfFile The uploaded resume PDF
 * @param jobDescription The text pasted by the user
 * @returns A promise that resolves to the ResumeResponse object
 */
export const optimizeResume = async (pdfFile: File, jobDescription: string): Promise<ResumeResponse> => {
  // We MUST use FormData to send files via HTTP
  const formData = new FormData();
  formData.append("pdf", pdfFile);
  formData.append("jobDescription", jobDescription);

  // POST request to http://localhost:8080/api/v1/resume/optimize
  const response = await apiClient.post<ResumeResponse>("/resume/optimize", formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });

  return response.data;
};
