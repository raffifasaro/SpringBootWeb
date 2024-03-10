import FullCalendar from '@fullcalendar/react'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from "@fullcalendar/interaction"
import React, { useState } from 'react';
import 'reactjs-popup/dist/index.css';
import Prompt from "./Prompt";



export default function Calendar() {
    const [clicked, setClicked] = useState(null);
    //input var
    const [inputValue, setInputValue] = useState('');

    const [timeValue, setTimeValue] = useState('');

    const handleDateClick = (arg) => {
        setClicked(arg.dateStr);

    }

    const handleClosePrompt = () => {
        setClicked(null);
    };

    const handlePromptSubmit = (value) => {
        setInputValue(value); // Update inputValue state
        setClicked(clicked);
    };

    const handlePromptTimeSubmit = (value) => {
        setTimeValue(value)
    };

    return (
        <div>
            {clicked !== null && (
                <Prompt defaultValue={clicked} onSubmit={handlePromptSubmit} onTimeSubmit={handlePromptTimeSubmit} onClose={handleClosePrompt} date={clicked}
                        inputValue={inputValue} timeValue={timeValue}/>  // Pass inputValue as prop
            )}

            <FullCalendar
                plugins={[dayGridPlugin, interactionPlugin]}
                dateClick={handleDateClick}
            />
            <p>
                Clicked date: {clicked ? clicked : 'None'}
            </p>
        </div>
    );
}
