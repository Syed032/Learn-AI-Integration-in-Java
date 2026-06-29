import { useState } from "react";
import { uploadPdf } from "../api/uploadApi";

const PdfUpload = () => {

    const [file, setFile] = useState<File | null>(null);

    const handleUpload = async () => {

        if (!file) {
            alert("Please select a PDF");
            return;
        }

        try {

            const result = await uploadPdf(file);

            alert(result);

        } catch {

            alert("PDF upload failed");
        }
    };

    return (
        <div className="upload-container">

            <input
                type="file"
                accept=".pdf"
                onChange={(e) => {
                    if (e.target.files?.length) {
                        setFile(e.target.files[0]);
                    }
                }}
            />

            <button onClick={handleUpload}>
                Upload PDF
            </button>

        </div>
    );
};

export default PdfUpload;