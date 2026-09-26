import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";

import Sidebar from "../components/Sidebar";
import API from "../services/api";

import jsPDF from "jspdf";
import html2canvas from "html2canvas";

function PrescriptionDetails() {

  const { id } = useParams();

  const [prescription, setPrescription] = useState(null);

  useEffect(() => {
    fetchPrescription();
  }, []);

  const fetchPrescription = async () => {

    try {

      const response = await API.get(
        `/api/prescriptions/${id}`
      );

      setPrescription(response.data);

    } catch (error) {

      console.error(error);

      alert("Unable to load prescription.");
    }
  };

  const downloadPDF = async () => {

    const input = document.getElementById("pdf-content");

    const canvas = await html2canvas(input, {
      scale: 2
    });

    const imgData = canvas.toDataURL("image/png");

    const pdf = new jsPDF("p", "mm", "a4");

    const pdfWidth = pdf.internal.pageSize.getWidth();

    const pageHeight = pdf.internal.pageSize.getHeight();

    const imgWidth = pdfWidth;

    const imgHeight =
      (canvas.height * imgWidth) / canvas.width;

    let heightLeft = imgHeight;
    let position = 0;

    pdf.addImage(
      imgData,
      "PNG",
      0,
      position,
      imgWidth,
      imgHeight
    );

    heightLeft -= pageHeight;

    while (heightLeft > 0) {

      position = heightLeft - imgHeight;

      pdf.addPage();

      pdf.addImage(
        imgData,
        "PNG",
        0,
        position,
        imgWidth,
        imgHeight
      );

      heightLeft -= pageHeight;
    }

    pdf.save(`Prescription_${prescription.id}.pdf`);
  };

  if (!prescription) {

    return (
      <div style={{ padding: "30px" }}>
        Loading...
      </div>
    );
  }

  return (
    <div style={{ display: "flex" }}>

      <Sidebar />

      <div
        style={{
          padding: "30px",
          width: "100%",
          background: "#f5f7fa",
          minHeight: "100vh"
        }}
      >

        <Link
          to="/history"
          style={{
            textDecoration: "none",
            color: "#1976d2",
            fontWeight: "bold"
          }}
        >
          ← Back to History
        </Link>

        <div
          style={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
            marginTop: "20px",
            marginBottom: "20px"
          }}
        >

          <h1>Prescription Details</h1>

          <button
            onClick={downloadPDF}
            style={{
              background: "#1976d2",
              color: "white",
              border: "none",
              padding: "12px 20px",
              borderRadius: "8px",
              cursor: "pointer",
              fontWeight: "bold",
              fontSize: "15px"
            }}
          >
            📄 Download PDF
          </button>

        </div>

        <div id="pdf-content">

          <hr />

          {prescription.imagePath && (

            <div
              style={{
                marginBottom: "30px",
                textAlign: "center"
              }}
            >

              <img
                src={`http://localhost:8080/uploads/${prescription.imagePath}`}
                alt="Prescription"
                style={{
                  width: "600px",
                  maxWidth: "100%",
                  borderRadius: "10px",
                  border: "1px solid #ddd",
                  boxShadow: "0 4px 10px rgba(0,0,0,0.15)"
                }}
              />

            </div>

          )}

          <h2>Patient Information</h2>

          <p>
            <strong>ID:</strong> {prescription.id}
          </p>

          <p>
            <strong>Patient:</strong> {prescription.patientName}
          </p>

          <p>
            <strong>Doctor:</strong> {prescription.doctorName}
          </p>

          <p>
            <strong>Specialization:</strong>{" "}
            {prescription.doctorSpecialization}
          </p>

          <p>
            <strong>Hospital:</strong>{" "}
            {prescription.hospitalName}
          </p>

          <p>
            <strong>Date:</strong>{" "}
            {prescription.prescriptionDate}
          </p>

          <hr />

          <h2>Medicines</h2>

          {prescription.medicines?.length > 0 ? (

            <ul>

              {prescription.medicines.map((medicine) => (

                <li key={medicine.id}>

                  <strong>{medicine.medicineName}</strong>

                  {medicine.dosage &&
                    <> - {medicine.dosage}</>
                  }

                  {medicine.frequency &&
                    <> | {medicine.frequency}</>
                  }

                </li>

              ))}

            </ul>

          ) : (

            <p>No medicines found.</p>

          )}

          <hr />

          <h2>OCR Extracted Text</h2>

          <textarea
            value={prescription.extractedText || ""}
            readOnly
            rows={15}
            style={{
              width: "100%",
              padding: "15px",
              borderRadius: "8px",
              border: "1px solid #ccc",
              resize: "none",
              background: "#fff"
            }}
          />

        </div>

      </div>

    </div>
  );
}

export default PrescriptionDetails;