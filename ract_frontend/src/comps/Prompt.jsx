import React, { useState } from 'react';
function Prompt({ onSubmit, date, inputValue }) { // Receive inputValue as prop
    const [internalInputValue, setInternalInputValue] = useState(inputValue); // Initialize with prop value
    let time_value = "!time not set!";

    const handleChange = (e) => {
        setInternalInputValue(e.target.value);
    };

    const handleSubmit = () => {
        onSubmit(internalInputValue); // Pass internalInputValue
    };

    return (
        <form className="box">
            <h1 className="label">Event on {date}:</h1>
            <div className="field">
                <label className="label">Time:</label>
                <div className="control">
                    <input className="input" type="number" placeholder="e.g. 19:45" value={time_value}/>
                </div>
            </div>

            <div className="field">
                <label className="label">Event on {date} at {time_value}:</label>
                <div className="control">
                    <input className="input" type="text" value={internalInputValue} onChange={handleChange}/>
                </div>
            </div>

            <button onClick={handleSubmit} className="button is-primary">Submit</button>
        </form>
    );

    //             <div className="field">
    //                 <label className="label">Event on {date} at {time_value}:</label>
    //                 <div className="control">
    //                     <input type="text" value={internalInputValue} onChange={handleChange}/>
    //                 </div>
    //             </div>

    //         <div>
    //             <div>Event on {date}:</div>
    //             <input type="text" value={internalInputValue} onChange={handleChange} />
    //             <button onClick={handleSubmit}>Submit</button>
    //         </div>

}

export default Prompt;

