import axios from "axios";

const API_BASE_URL = "http://localhost:8080";

export const uploadPdf = async (file: File) => {

    const formData = new FormData();

    formData.append("file", file);

    const response = await axios.post(
        `${API_BASE_URL}/ai/upload`,
        formData,
        {
            headers: {
                "Content-Type": "multipart/form-data"
            }
        }
    );

    return response.data;
};