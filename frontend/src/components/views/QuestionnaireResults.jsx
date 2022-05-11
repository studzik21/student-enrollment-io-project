import React from "react";
import { Table } from "../table";
import { useParams } from "react-router-dom";
import { useEffect } from "react";
import http from "../../services/http";
import { useState } from "react";
import { Spinner } from "react-bootstrap";

function QuestionnaireResults() {
    const { id } = useParams();
    const [state, setState] = useState({
        tableInfo: null,
        loading: true
    });
    useEffect(() => {
        (async function () {
            const reqResult = await http.get(`/vote/${id}`);
            setState({ loading: false, tableInfo: reqResult["data"] });
        })();
    }, [id]);
    return (
        <>
            {state.loading ? (
                <>
                    <Spinner animation="border" />
                </>
            ) : (
                <div className="resultsView">
                    <Table
                        headers={state.tableInfo.headers}
                        records={state.tableInfo.rows}
                        lambda={(cell) => (cell === 1 ? "bg-success text-white" : "bg-light")}
                    />
                </div>
            )}
        </>
    );
}
export default QuestionnaireResults;
